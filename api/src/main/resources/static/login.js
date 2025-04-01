document.getElementById('login-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const loginData = {
        email: document.getElementById('email').value,
        password: document.getElementById('password').value
    };

    fetch('/api/products/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(loginData)
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(err => { throw err; }).catch(() => { throw new Error(response.statusText); });
        }
        return response.json();
    })
    .then(data => {
        console.log('User logged in:', data);
        alert('Login successful!');
        localStorage.setItem('userEmail', loginData.email);
        window.location.href = 'index.html';
    })
    .catch(error => {
        console.error('Error logging in user:', error);
        alert('Error: ' + (error.error || error.message || 'Error logging in user'));
    });
});

function handleCredentialResponse(response) {
    const data = jwt_decode(response.credential);
    const email = data.email;

    // Save email to localStorage
    localStorage.setItem('userEmail', email);

    // Redirect to the main page
    window.location.href = 'index.html';
}

window.onload = function () {
    google.accounts.id.initialize({
        client_id: 'YOUR_GOOGLE_CLIENT_ID',
        callback: handleCredentialResponse
    });
    google.accounts.id.renderButton(
        document.getElementById('google-signin-button'),
        { theme: 'outline', size: 'large' }
    );
    google.accounts.id.prompt();
};
