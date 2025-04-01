document.addEventListener('DOMContentLoaded', function() {
    fetch('/api/products/coupons')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector('#coupon-table tbody');
            data.forEach(coupon => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${coupon.id}</td>
                    <td>${coupon.code}</td>
                    <td>${coupon.discountPercentage}%</td>
                    <td>${coupon.productId}</td>
                    <td>${coupon.productTitle}</td>  <!-- Add this column data -->
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error fetching coupons:', error);
        });
});
