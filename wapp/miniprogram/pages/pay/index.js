const BASE_URL = 'http://localhost:8080';

Page({
  data: {
    // 订单信息
    orderNumber: '',
    orderAmount: '0.00',

    // 流程控制: 'choose' | 'wechat' | 'alipay'
    step: 'choose',

    // 支付方式：0=未选择, 1=微信支付, 2=支付宝
    payMethod: 0,

    // 支付宝相关
    qrCodeUrl: '',
    loading: false,
    checking: false,
    errorMsg: '',

    // 微信支付相关
    paying: false
  },

  onLoad(options) {
    const { orderNumber, orderAmount } = options;
    if (orderNumber) {
      this.setData({ orderNumber, orderAmount: orderAmount || '0.00' });
    } else {
      wx.showToast({ title: '缺少订单信息', icon: 'none' });
    }
  },

  // ========== 选择支付方式 ==========
  selectMethod(e) {
    const method = e.currentTarget.dataset.method;
    this.setData({ payMethod: method });
  },

  // 确认支付 → 根据选择的支付方式进入对应流程
  confirmPay() {
    const { payMethod } = this.data;
    if (payMethod === 1) {
      // 微信支付
      this.setData({ step: 'wechat' });
    } else if (payMethod === 2) {
      // 支付宝
      this.setData({ step: 'alipay', errorMsg: '' });
      this.generateQR();
    }
  },

  // 返回选择支付方式
  backToChoose() {
    this.setData({ step: 'choose', errorMsg: '', qrCodeUrl: '' });
  },

  // ========== 支付宝：生成二维码 ==========
  generateQR() {
    this.setData({ loading: true, errorMsg: '' });

    wx.request({
      url: `${BASE_URL}/user/order/payment`,
      method: 'POST',
      header: {
        'Content-Type': 'application/json',
        'Authorization': wx.getStorageSync('token') || ''
      },
      data: {
        orderNumber: this.data.orderNumber,
        payMethod: 2   // 支付宝
      },
      success: (res) => {
        if (res.data && res.data.code === 1 && res.data.data) {
          this.setData({
            qrCodeUrl: res.data.data,
            loading: false
          });
        } else {
          this.setData({
            loading: false,
            errorMsg: res.data?.msg || '生成支付二维码失败'
          });
        }
      },
      fail: (err) => {
        console.error('支付请求失败：', err);
        this.setData({
          loading: false,
          errorMsg: '网络请求失败，请检查网络后重试'
        });
      }
    });
  },

  // 支付宝：手动查询支付状态
  checkPayStatus() {
    if (this.data.checking) return;
    this.setData({ checking: true });

    wx.request({
      url: `${BASE_URL}/user/order/queryPayStatus/${this.data.orderNumber}`,
      method: 'GET',
      header: {
        'Authorization': wx.getStorageSync('token') || ''
      },
      success: (res) => {
        if (res.data && res.data.code === 1 && res.data.data === true) {
          wx.showToast({ title: '支付成功！', icon: 'success', duration: 1500 });
          setTimeout(() => {
            wx.redirectTo({
              url: `/pages/paySuccess/index?orderNumber=${this.data.orderNumber}&orderAmount=${this.data.orderAmount}`
            });
          }, 1500);
        } else {
          wx.showToast({
            title: '未检测到支付，请确认已扫码付款',
            icon: 'none',
            duration: 2000
          });
          this.setData({ checking: false });
        }
      },
      fail: () => {
        wx.showToast({ title: '查询失败，请重试', icon: 'none' });
        this.setData({ checking: false });
      }
    });
  },

  // ========== 微信支付 ==========
  wechatPay() {
    if (this.data.paying) return;
    this.setData({ paying: true });

    wx.request({
      url: `${BASE_URL}/user/order/payment`,
      method: 'POST',
      header: {
        'Content-Type': 'application/json',
        'Authorization': wx.getStorageSync('token') || ''
      },
      data: {
        orderNumber: this.data.orderNumber,
        payMethod: 1   // 微信支付
      },
      success: (res) => {
        if (res.data && res.data.code === 1 && res.data.data) {
          const payParams = res.data.data;
          // 调用微信支付JSAPI
          wx.requestPayment({
            timeStamp: payParams.timeStamp,
            nonceStr: payParams.nonceStr,
            package: payParams.packageStr,
            signType: payParams.signType || 'RSA',
            paySign: payParams.paySign,
            success: () => {
              wx.showToast({ title: '支付成功！', icon: 'success', duration: 1500 });
              setTimeout(() => {
                wx.redirectTo({
                  url: `/pages/paySuccess/index?orderNumber=${this.data.orderNumber}&orderAmount=${this.data.orderAmount}`
                });
              }, 1500);
            },
            fail: (err) => {
              console.error('微信支付取消/失败：', err);
              wx.showToast({ title: '支付取消或失败', icon: 'none' });
              this.setData({ paying: false });
            }
          });
        } else {
          wx.showToast({ title: res.data?.msg || '获取支付参数失败', icon: 'none' });
          this.setData({ paying: false });
        }
      },
      fail: () => {
        wx.showToast({ title: '网络请求失败', icon: 'none' });
        this.setData({ paying: false });
      }
    });
  }
});
