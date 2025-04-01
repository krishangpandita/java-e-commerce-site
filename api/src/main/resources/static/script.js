document.addEventListener('DOMContentLoaded', function() {
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
    const searchTitleBox = document.getElementById('search-title-box');
    const searchTitleButton = document.getElementById('search-title-button');
    const searchBrandBox = document.getElementById('search-brand-box');
    const searchBrandButton = document.getElementById('search-brand-button');
    const tableBody = document.querySelector('#product-table tbody');
    const cartButton = document.getElementById('cart-button');

    const fetchProducts = (title = '', brand = '') => {
        let url = '/api/products/all';
        if (title) {
            url = `/api/products?title=${encodeURIComponent(title)}`;
        } else if (brand) {
            url = `/api/products?brand=${encodeURIComponent(brand)}`;
        }

        fetch(url)
            .then(response => response.json())
            .then(data => {
                tableBody.innerHTML = ''; // Clear the table
                let serialNumber = 1; // Initialize the serial number

                data.forEach(product => {
                    const row = document.createElement('tr');
                    const cartItem = cart.find(item => item.id === product.id);
                    const isOutOfStock = product.stock === 0;

                    row.innerHTML = `
                        <td>${serialNumber++}</td> <!-- Serial number -->
                        <td>${product.title}</td>
                        <td>${product.stock}</td>
                        <td>${product.price}</td>
                        <td>${product.discountPercentage}%</td>
                        <td>${product.rating}</td>
                        <td>${product.brand}</td>
                        <td><img src="${product.thumbnail}" alt="${product.title}" /></td>
                        <td>${product.images.map(image => `<img src="${image}" alt="${product.title}" />`).join('')}</td>
                        <td>
                            <input type="number" class="quantity-input" data-id="${product.id}" value="${cartItem ? cartItem.quantity : 1}" min="1" max="${product.stock}" ${isOutOfStock ? 'disabled' : ''}>
                        </td>
                        <td>
                            <button class="add-to-cart" data-id="${product.id}" ${isOutOfStock ? 'disabled' : ''}>${cartItem ? '✔️ Added' : 'Add to Cart'}</button>
                        </td>
                    `;

                    tableBody.appendChild(row);
                });

                // Add event listeners for the Add to Cart buttons
                document.querySelectorAll('.add-to-cart').forEach(button => {
                    button.addEventListener('click', function() {
                        const productId = this.getAttribute('data-id');
                        const quantityInput = document.querySelector(`.quantity-input[data-id="${productId}"]`);
                        const quantity = parseInt(quantityInput.value);
                        const stock = parseInt(quantityInput.getAttribute('max'));

                        if (quantity > stock) {
                            alert('The quantity exceeds the available stock.');
                            return;
                        }

                        let cartItem = cart.find(item => item.id === productId);
                        if (cartItem) {
                            cartItem.quantity = Math.min(cartItem.quantity + quantity, stock); // Increment the quantity within stock limit
                        } else {
                            cart.push({ id: productId, quantity });
                        }

                        localStorage.setItem('cart', JSON.stringify(cart));
                        this.innerText = '✔️ Added'; // Update button text to indicate the item is added
                    });
                });
            })
            .catch(error => {
                console.error('Error fetching data:', error);
            });
    };

    searchTitleButton.addEventListener('click', () => {
        fetchProducts(searchTitleBox.value, '');
    });

    searchBrandButton.addEventListener('click', () => {
        fetchProducts('', searchBrandBox.value);
    });

    cartButton.addEventListener('click', () => {
        window.location.href = 'cart.html';
    });

    // Initial fetch to display all products
    fetchProducts();
});
