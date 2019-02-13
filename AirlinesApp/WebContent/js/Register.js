$(document).ready(function(){
	
	var usernameInput = $('#usernameInput');
	var passwordInput = $('#passwordInput');
	var repeatedPasswordInput = $('#repeatedPasswordInput')
	
	var messageParagraph = $('#messageParagraph');
	
	$('#registrationSubmit').on('click', function(event){
		var username = usernameInput.val();
		var password = passwordInput.val();
		var repeatedPassword = repeatedPasswordInput.val();
		
		console.log('username: ', username);
		console.log('password: ', password);
		console.log('repatedPassword: ', repeatedPassword);
		
		if(repeatedPassword != password) {
			
			alert('Passwords do not match');
			
			event.preventDefault();
			return false;
		}
		
		var params = {
			'username': username,
			'password' : password
		}
		$.post('RegisterServlet', params, function(data){
			console.log(data);
			
			if(data.status == 'failure') {
				alert(data.message)
				return;
			}
			
			if(data.status == 'success') {
				alert("Successful registration")
				window.location.replace('login.html');
			}
		})
		
		event.preventDefault();
		return false;
		
	})
})