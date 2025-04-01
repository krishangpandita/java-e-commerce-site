document.getElementById('add-product-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the default form submission

    const title = document.getElementById('title').value;
    const stock = document.getElementById('stock').value;
    const price = document.getElementById('price').value;
    const discountPercentage = document.getElementById('discountPercentage').value;
    const rating = document.getElementById('rating').value;
    const brand = document.getElementById('brand').value;
    const thumbnail = document.getElementById('thumbnail').value;
    const images = document.getElementById('images').value.split(',').map(url => url.trim());

    const productData = {
        title: title,
        stock: stock,
        price: price,
        discountPercentage: discountPercentage,
        rating: rating,
        brand: brand,
        thumbnail: thumbnail,
        images: images
    };

    fetch('/api/products', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(productData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        alert('Product added successfully!');
        window.location.href = 'index.html'; // Redirect to the index page after successful addition
    })
    .catch(error => {
        console.error('Error adding product:', error);
        alert('Failed to add product. Please try again.');
    });
});
