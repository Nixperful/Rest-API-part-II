
var info = {
    "order_id": 1,
    "table_id": 1,
    "products": [

        {
            "product": "Pizza",
            "quantity": 3,
            "price": "$10000"
        },

        {
            "product": "HotDog",
            "quantity": 1,
            "price": "$3000"
        },

        {
            "product": "Coke",
            "quantity": 1,
            "price": "$1300"
        }
    ]
}


var orderNumber = 1;
var orders = [];
function addOrder() {
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
        var tble = document.createTextNode("Table # " + info.order_id.toString());
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
        for (var i = 0; i < info.products.length; i++) {
            var tr = document.createElement('tr');

            var td = document.createElement('td');
            td.appendChild(document.createTextNode(info.products[i].product));
            tr.appendChild(td);

            var td = document.createElement('td');
            td.appendChild(document.createTextNode(info.products[i].quantity));
            tr.appendChild(td);            
            tbdy.appendChild(tr);
        }
        ord.appendChild(tbdy);

        ordersTable.appendChild(tags);
        ordersTable.appendChild(tabl);
        ordersTable.appendChild(ord);
        
}

function removeOrderById(id) {
    var ordersTable = document.getElementById('tableMenu');
    for (var i = 0; i < ordersTable.childNodes.length; i++) {
        if (ordersTable.childNodes[i].table_id == id) {
            ordersTable.removeChild(i);
        }
    }
}

function loadOrders() {
    
    axios.get('/orders')
        .then(function (response) {
            list = response.data;
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
                var tble = document.createTextNode("Table # " + numberTable );
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
                for ( producto in list[order].orderAmountsMap) {
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
            
        })
        .catch(function (error) {
            console.log(error);
            failMessage();
        });
    function failMessage() {
        alert("There is a problem with our servers. We apologize for the inconvince, please try again later");
    }
}



