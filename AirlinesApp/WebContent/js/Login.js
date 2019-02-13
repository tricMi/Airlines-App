$(document).ready(function(){
	
	var usernameInput = $("#usernameInput");
	var passwordInput = $("#passwordInput");
	
	$('#loginSubmit').on('click', function(event){
		var username = usernameInput.val();
		var password = passwordInput.val();
		
		console.log('username: ', username);
		console.log('password: ', password);

		if(username == ""){
			alert("You must enter username")

			event.preventDefault()
			return false
		}

		if(password == ""){
			alert("You must enter a password")

			event.preventDefault()
			return false
		}
		
		var params = {
			'username' : username,
			'password' : password
		}
		
		$.post('LoginServlet', params, function(data) {
			console.log('Answer is delivered');
			console.log(data);
			
			if (data.status == 'failure')
			{
				alert("Username or password isn't correct")
				usernameInput.val('');
				passwordInput.val('');
				
				return;
			}
			
			if(data.status == 'success'){
				window.location.replace('AdminWindow.html')
			}
		});
		
		console.log('Request is sent');
		
		event.preventDefault();
		return false;
	});
})