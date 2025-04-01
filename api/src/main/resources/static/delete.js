document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('delete-product-form');
    const message = document.getElementById('message');

    form.addEventListener('submit', function(event) {
        event.preventDefault();
        const productId = document.getElementById('product-id').value;

        fetch(`/api/products/${productId}`, {
            method: 'DELETE',
        })
        .then(response => {
            if (response.ok) {
                message.textContent = `Product with ID ${productId} deleted successfully.`;
                message.style.color = 'green';
            } else if (response.status === 404) {
                message.textContent = `Product with ID ${productId} not found.`;
                message.style.color = 'red';
            } else {
                message.textContent = 'Failed to delete product.';
                message.style.color = 'red';
            }
        })
        .catch(error => {
            console.error('Error deleting product:', error);
            message.textContent = 'Error deleting product. Please try again.';
            message.style.color = 'red';
        });
    });
});
