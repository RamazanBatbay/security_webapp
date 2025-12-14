document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');

    // Visual enhancement: focus the username field on load
    const usernameInput = document.getElementById('username');
    if (usernameInput) {
        usernameInput.focus();
    }

    // Optional: Add client-side pre-validation or animation hooks here
    loginForm.addEventListener('submit', (e) => {
        const btn = loginForm.querySelector('button[type="submit"]');
        if (btn) {
            btn.innerHTML = 'Signing In...';
            btn.style.opacity = '0.8';
        }
    });
});
