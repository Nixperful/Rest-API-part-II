var RestControllerModule = (function () {

  var getOrders = function (callback) {
      axios.get('/orders')
          .then(function (response) {
              callback.onSuccess(response);
              //callback.onFailure(response);
          })
  }

  var updateOrder = function (order, callback) {
    // todo implement
  };

  var deleteOrder = function (orderId, callback) {
    // todo implement
  };

  var createOrder = function (order, callback) {
    // todo implement
  };

  return {
    getOrders: getOrders,
    updateOrder: updateOrder,
    deleteOrder: deleteOrder,
    createOrder: createOrder
  };

})();