document.getElementById('registration-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const mobileNumber = document.getElementById('mobile_no').value;
    const errorMessage = document.getElementById('error-message');

    // Clear any previous error message
    if (errorMessage) {
        errorMessage.remove();
    }

    // Validate mobile number length
    if (mobileNumber.length !== 10) {
        const errorElement = document.createElement('p');
        errorElement.id = 'error-message';
        errorElement.style.color = 'red';
        errorElement.innerText = 'Mobile number must be exactly 10 digits long.';
        document.getElementById('registration-form').appendChild(errorElement);
        return;
    }

    const formData = {
        username: document.getElementById('username').value,
        email: document.getElementById('email').value,
        password: document.getElementById('password').value,
        address: document.getElementById('address').value,
        pincode: document.getElementById('pincode').value,
        mobile_no: mobileNumber
    };

    fetch('/api/products/register', {  // Ensure the endpoint matches the backend URL
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(err => { throw err; }).catch(() => { throw new Error(response.statusText); });
        }
        return response.json();
    })
    .then(data => {
        console.log('User registered:', data);
        alert('User registered successfully!');
        document.getElementById('registration-form').reset();
    })
    .catch(error => {
        console.error('Error registering user:', error);
        alert('Error: ' + (error.error || error.message || 'Error registering user'));
    });
});
