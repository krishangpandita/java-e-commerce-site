document.addEventListener('DOMContentLoaded', function() {
    const cart = JSON.parse(localStorage.getItem('cart')) || [];

    if (cart.length === 0) {
        document.querySelector('#cart-table tbody').innerHTML = '<tr><td colspan="12">Your cart is empty</td></tr>';
        displayTotalCost(0, 0, 0, true); // Pass an additional parameter to indicate the cart is empty
        return;
    }

    fetch('/api/products/all')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector('#cart-table tbody');
            let serialNumber = 1; // Initialize the serial number

            cart.forEach(cartItem => {
                const product = data.find(p => p.id === parseInt(cartItem.id));
                if (product) {
                    const totalAmount = product.price * cartItem.quantity;

                    const row = document.createElement('tr');

                    row.innerHTML = `
                        <td>${serialNumber++}</td> <!-- Product number -->
                        <td>${product.title}</td>
                        <td>${product.stock || 'N/A'}</td>
                        <td>${product.price || 'N/A'}</td>
                        <td>${product.discountPercentage || 'N/A'}%</td>
                        <td>${product.rating || 'N/A'}</td>
                        <td>${product.brand || 'N/A'}</td>
                        <td><img src="${product.thumbnail}" alt="${product.title}" /></td>
                        <td>${product.images.map(image => `<img src="${image}" alt="${product.title}" />`).join('') || 'N/A'}</td>
                        <td>
                            <input type="number" class="quantity-input" data-id="${product.id}" value="${cartItem.quantity}" min="1" max="${product.stock}">
                        </td>
                        <td class="total-amount" data-id="${product.id}">${totalAmount.toFixed(2)}</td>
                        <td><button class="remove-from-cart" data-id="${product.id}">Remove from Cart</button></td>
                    `;

                    tableBody.appendChild(row);
                }
            });

            calculateAndDisplayTotalCost();

            document.querySelectorAll('.quantity-input').forEach(input => {
                input.addEventListener('change', function() {
                    const productId = this.getAttribute('data-id');
                    const newQuantity = parseInt(this.value);
                    const maxStock = parseInt(this.getAttribute('max'));

                    if (newQuantity > maxStock) {
                        alert('The quantity exceeds the available stock.');
                        this.value = maxStock; // Reset to max stock if exceeded
                    }

                    updateCart(productId, Math.min(newQuantity, maxStock));
                });
            });

            document.querySelectorAll('.remove-from-cart').forEach(button => {
                button.addEventListener('click', function() {
                    const productId = this.getAttribute('data-id');
                    removeFromCart(productId);
                });
            });
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });

    // Add event listener for applying coupon
    document.getElementById('applyCouponButton').addEventListener('click', function() {
        const couponCode = document.getElementById('couponCode').value;
        applyCoupon(couponCode);
    });

    // Add event listener for "Confirm Order" button
    document.getElementById('placeOrderButton').addEventListener('click', function() {
        window.location.href = 'Order.html';
    });
});

function calculateAndDisplayTotalCost() {
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
    let totalCost = 0;

    fetch('/api/products/all')
        .then(response => response.json())
        .then(data => {
            cart.forEach(cartItem => {
                const product = data.find(p => p.id === parseInt(cartItem.id));
                if (product) {
                    totalCost += product.price * cartItem.quantity;
                }
            });

            let discount = cart.discount || 0;
            let automaticDiscount = totalCost >= 5000 ? totalCost * 0.2 : 0;
            discount = discount; // Do not include automatic discount in the coupon discount

            displayTotalCost(totalCost, discount, automaticDiscount, cart.length === 0);
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
}

function displayTotalCost(totalCost, discount = 0, automaticDiscount = 0, isCartEmpty = false) {
    let totalCostElement = document.getElementById('total-cost');
    const finalCost = totalCost - automaticDiscount - discount;

    // Remove existing calculation breakdown if any
    const existingBreakdown = document.querySelector('.calculation-breakdown');
    if (existingBreakdown) {
        existingBreakdown.remove();
    }

    if (isCartEmpty) {
        if (totalCostElement) {
            totalCostElement.innerHTML = '';
        }
        return;
    }

    const breakdownHtml = `
        <div class="calculation-breakdown">
            <p>Total Amount: ${totalCost.toFixed(2)}</p>
            <p>Coupon Discount: ${discount.toFixed(2)}</p>
            <p><strong>Total Discounted Price: ${finalCost.toFixed(2)}</strong></p>
        </div>
        <button id="confirmOrderButton">Confirm Order</button>
    `;

    if (totalCostElement) {
        totalCostElement.innerHTML = breakdownHtml;
    } else {
        const footerRow = document.createElement('tr');
        footerRow.innerHTML = `
            <td colspan="11" style="text-align: right; font-weight: bold;">Total Cost:</td>
            <td id="total-cost">
                ${breakdownHtml}
            </td>
            <td></td>
        `;
        document.querySelector('#cart-table tbody').appendChild(footerRow);
    }

    // Add event listener for "Confirm Order" button after creating it
    document.getElementById('confirmOrderButton').addEventListener('click', function() {
        window.location.href = 'Order.html';
    });
}

function updateCart(productId, newQuantity) {
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    cart = cart.map(item => {
        if (item.id === productId) {
            item.quantity = newQuantity;
        }
        return item;
    });
    localStorage.setItem('cart', JSON.stringify(cart));

    // Update the total amount for the updated product
    fetch('/api/products/all')
        .then(response => response.json())
        .then(data => {
            const product = data.find(p => p.id === parseInt(productId));
            if (product) {
                const totalAmount = product.price * newQuantity;
                document.querySelector(`.total-amount[data-id="${productId}"]`).innerText = totalAmount.toFixed(2);

                // Display message indicating quantity is updated
                const quantityInput = document.querySelector(`.quantity-input[data-id="${productId}"]`);
                const message = document.createElement('div');
                message.innerText = 'Quantity is updated';
                message.style.color = 'green';
                message.style.fontWeight = 'bold';
                message.style.marginTop = '5px';
                quantityInput.parentElement.appendChild(message);

                setTimeout(() => {
                    message.remove();
                }, 2000); // Remove the message after 2 seconds
            }

            calculateAndDisplayTotalCost();
        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
}

function removeFromCart(productId) {
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    cart = cart.filter(item => item.id !== productId);
    localStorage.setItem('cart', JSON.stringify(cart));
    calculateAndDisplayTotalCost();
    location.reload();
}

function applyCoupon(couponCode) {
    fetch('/api/products/coupons')
        .then(response => response.json())
        .then(coupons => {
            const validCoupon = coupons.find(coupon => coupon.code === couponCode);
            if (validCoupon) {
                const cart = JSON.parse(localStorage.getItem('cart')) || [];
                let totalCost = 0;
                let discount = 0;

                fetch('/api/products/all')
                    .then(response => response.json())
                    .then(data => {
                        cart.forEach(cartItem => {
                            const product = data.find(p => p.id === parseInt(cartItem.id));
                            if (product) {
                                totalCost += product.price * cartItem.quantity;
                                if (validCoupon.id === 1) {
                                    // Apply universal coupon to the total cart cost
                                    discount = totalCost * validCoupon.discountPercentage / 100;
                                } else if (validCoupon.productId === product.id) {
                                    // Apply specific product coupon
                                    discount += (product.price * cartItem.quantity * validCoupon.discountPercentage) / 100;
                                }
                            }
                        });

                        const automaticDiscount = totalCost >= 5000 ? totalCost * 0.2 : 0;

                        cart.discount = discount; // Do not include automatic discount in the coupon discount
                        localStorage.setItem('cart', JSON.stringify(cart));
                        displayTotalCost(totalCost, discount, automaticDiscount, cart.length === 0);
                        console.log(`Coupon applied! You saved ${discount.toFixed(2)}. New total: ${(totalCost - automaticDiscount - discount).toFixed(2)}`);
                    })
                    .catch(error => {
                        console.error('Error fetching data:', error);
                    });
            } else {
                console.log('Invalid coupon code');
            }
        })
        .catch(error => {
            console.error('Error fetching coupons:', error);
        });
}

