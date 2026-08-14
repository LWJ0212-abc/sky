Page({
  data: {
    orderNumber: '',
    orderAmount: '0.00'
  },

  onLoad(options) {
    const { orderNumber, orderAmount } = options;
    this.setData({
      orderNumber: orderNumber || '',
      orderAmount: orderAmount || '0.00'
    });
  },

  // 查看订单
  goToOrder() {
    wx.reLaunch({
      url: '/pages/index/index'
    });
  },

  // 返回首页
  goHome() {
    wx.reLaunch({
      url: '/pages/index/index'
    });
  }
});
