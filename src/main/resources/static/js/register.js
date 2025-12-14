document.addEventListener('DOMContentLoaded', () => {
    const registerForm = document.getElementById('registerForm');
    const passwordInput = document.getElementById('password');
    const confirmPasswordInput = document.getElementById('confirmPassword');

    registerForm.addEventListener('submit', (e) => {
        if (passwordInput.value !== confirmPasswordInput.value) {
            e.preventDefault();
            alert("Passwords do not match!");
            confirmPasswordInput.focus();
            return;
        }

        const btn = registerForm.querySelector('button[type="submit"]');
        if (btn) {
            btn.innerHTML = 'Creating Account...';
            btn.style.opacity = '0.8';
        }
    });
});
