function addToCart(productId) {
    event.preventDefault()
    fetch("/api/cart", {
        method: 'post',
        body: JSON.stringify({
            "productId": productId
        }),
        headers: {
            "Content-Type": "application/json"
        }
    }).then(function (res) {
        console.info(res)
        return res.json()
    }).then(function (data) {
        let counter = document.querySelector('.number-buy')
        counter.innerText = data
    }).catch((error) => {
        console.error('Error:', error);
    });
}

function updateCartItem(obj, id) {
    fetch("/api/updateQuantity", {
        method: 'put',
        body: JSON.stringify({
            "productId": id,
            "price": 0,
            "quantity": obj.value
        }),
        headers: {
            "Content-Type": "application/json"
        }
    }).then(function (res) {
        return res.json()
    }).then(function (data) {
        let counter = document.querySelector('.number-buy')
        counter.innerText = data.counter
        let amount = document.querySelector('.tongtien')
        amount.innerText = data.amount
        location.reload()
    });
}

function deleteCartItem(id) {
    if (confirm("Bạn có muốn xóa sản phẩm này không?? ") == true) {
        fetch(`/api/cart/${id}`, {
            method: 'delete'
        }).then(function (res) {
            return res.json()
        }).then(function (data) {
            let counter = document.querySelector('.number-buy')
            counter.innerText = data.counter
            let amount = document.querySelector('.tongtien')
            amount.innerText = data.amount
            location.reload()
        });

    }
}


