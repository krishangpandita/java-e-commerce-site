document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('update-product-form');
    const message = document.getElementById('message');

    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const productId = document.getElementById('product-id').value;
        const productData = {
            id: productId,
            title: document.getElementById('title').value,
            stock: document.getElementById('stock').value,
            price: document.getElementById('price').value,
            discountPercentage: document.getElementById('discount').value,
            rating: document.getElementById('rating').value,
            brand: document.getElementById('brand').value,
            thumbnail: document.getElementById('thumbnail').value,
            images: document.getElementById('images').value.split(',')
        };

        fetch(`/api/products/${productId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(productData)
        })
        .then(response => {
            if (response.ok) {
                message.textContent = `Product with ID ${productId} updated successfully.`;
                message.style.color = 'green';
            } else if (response.status === 404) {
                message.textContent = `Product with ID ${productId} not found.`;
                message.style.color = 'red';
            } else {
                message.textContent = 'Failed to update product.';
                message.style.color = 'red';
            }
        })
        .catch(error => {
            console.error('Error updating product:', error);
            message.textContent = 'Error updating product. Please try again.';
            message.style.color = 'red';
        });
    });
});
