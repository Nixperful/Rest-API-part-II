var OrdersControllerModule = (function () {

  var showOrdersByTable = function () {
    //Todo implement

    var callback = {

        onSuccess: function(ordersList){
            list = ordersList;
            document.getElementById('tableMenu').innerHTML = "";
            for (order in list) {
                var ordersTable = document.getElementById('tableMenu');
                var ord = document.createElement('table');
                ord.style.width = '100%';
                ord.style.backgroundColor = "#345";
                ord.style.textAlign = "center";
                ord.style.margin = "auto";
                ord.style.border = "1px solid black";
                ord.style.borderCollapse = "collapse";

                orderNumber = orderNumber + 1;
                var tags = document.createElement('p');
                var bold = document.createElement('b');
                var number = document.createTextNode("Order # " + orderNumber.toString());
                tags.style.color = "#B22222";
                bold.appendChild(number);
                tags.appendChild(bold);


                var tabl = document.createElement('p');
                var numberTable = "" + list[order].tableNumber;
                var tble = document.createTextNode("Table # " + numberTable);
                tabl.appendChild(tble);


                var tr = document.createElement('tr');
                var th1 = document.createElement('th');
                th1.style.backgroundColor = "#3a82d2";
                var c1 = document.createTextNode("Product");
                th1.appendChild(c1);
                th2 = document.createElement('th');
                th2.style.backgroundColor = "#3a82d2";
                var c2 = document.createTextNode("Quantity");
                th2.appendChild(c2);
                tr.appendChild(th1);
                tr.appendChild(th2);
                ord.appendChild(tr);
                var tbdy = document.createElement('tbody');
                for (producto in list[order].orderAmountsMap) {
                    var tr = document.createElement('tr');
                    var td = document.createElement('td');
                    td.appendChild(document.createTextNode(producto));
                    tr.appendChild(td);

                    var td = document.createElement('td');
                    td.appendChild(document.createTextNode(list[order].orderAmountsMap[producto]));
                    tr.appendChild(td);
                    tbdy.appendChild(tr);
                }
                ord.appendChild(tbdy);

                ordersTable.appendChild(tags);
                ordersTable.appendChild(tabl);
                ordersTable.appendChild(ord);
            }

        

        },
        onFailed: function(exception){
            console.log(error);
            alert("There is a problem with our servers. We apologize for the inconvince, please try again later");
        }
    }
    //RestaurantRestController.getOrders(callback)
  };

  var updateOrder = function () {
    // todo implement
  };

  var deleteOrderItem = function (itemName) {
    // todo implement
  };

  var addItemToOrder = function (orderId, item) {
    // todo implement
  };

  return {
    showOrdersByTable: showOrdersByTable,
    updateOrder: updateOrder,
    deleteOrderItem: deleteOrderItem,
    addItemToOrder: addItemToOrder
  };

})();