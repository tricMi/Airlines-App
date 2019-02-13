$(document).ready(function(){
	$('#logoutLink').on('click', function(event) {
		$.get('LogoutServlet', function(data) {
			console.log(data);

			if (data.status == 'unauthenticated') {
				window.location.replace('login.html');
				return;
			}
		});
	
		event.preventDefault();
		return false;
	});
	
	var id = window.location.search.slice(1).split('&')[0].split('=')[1];
	console.log(id);
	
	function getUser() {
		$.get("UserProfileServlet", {'id' : id}, function(data){
			console.log(data)
			
			if (data.status == 'unauthenticated') {
				window.location.replace('login.html');
				return;
			}
			
			if(data.status == 'success'){
				console.log('whaaaaaaaaaaaaat?')
				var user = data.user
			$.get('UserServlet', {'action': 'loggedInUserRole'}, function(data){

				if (data.status == 'unauthenticated') {
					window.location.replace('login.html');
					return;
				}

				if(data.loggedInUserRole == 'ADMIN'){
						
						var usernameInput = $('#usernameInput')
						var passwordInput = $('#passwordInput')
						var registerDateInput = $('#registerDateInput');
						var roleChecked = document.getElementById('roleInput')
						var blockedUserChecked = document.getElementById('blockedUserInput')
						
						
						usernameInput.prop('disabled', true)
						usernameInput.val(user.username)
						passwordInput.val(user.password)
						var date = new Date(parseInt(user.registrationDate))
						var result = date.format('mm/dd/yyyy')
						if(user.role == 'ADMIN') {
							roleChecked.selectedIndex = "1"
						}
						if(user.blockedUser == true) {
							blockedUserChecked.selectedIndex = "1"
						}
						registerDateInput.text(result)
						
						
						$('#updateSubmit').on('click', function(event){
							var conf = confirm("Are you sure that you want to change data?")
							if(conf == false){
								return false;
							}else {

							var password = passwordInput.val()
							var role = roleChecked.options[roleChecked.selectedIndex].text
							var blockedUser = blockedUserChecked.options[blockedUserChecked.selectedIndex].text

							if(password == ""){
								alert("You must enter a password")
								event.preventDefault()
								return false
							}

							if(blockedUser.selectedIndex == "-1"){
								alert("You must select a type")
								event.preventDefault()
								return false
							}

							if(role.selectedIndex == "-1"){
								alert("You must select a role")
								event.preventDefault()
								return false
							}
							
							params = {
								'action' : 'editByAdmin',
								'id' : id,
								'password' : password,
								'role' : role,
								'blockedUser' : blockedUser
							}
							console.log(params)
							
							$.post('UserProfileServlet', params, function(data){
								
								if (data.status == 'unauthenticated') {
									window.location.replace('login.html');
									return;
								}

								if(data.status == 'falure'){
									alert("Something went wrong")
								}
								
								if(data.status == 'success'){
									alert("Successfull update")
									window.location.replace('AdminWindow.html')
									return;
								}
								
							})
							
							event.preventDefault();
							return false;
							}
						})
				
			}
				
				else if(data.loggedInUserRole == 'USER') {
					$('#adminNav').hide()
					console.log('fjjjjjjjjjjjjjjjjjjjjjjjjjjj')
					$('#userView').hide()
							$('#roleInput').prop("disabled", true)
							$('#blockedUserInput').prop("disabled", true)
							var usernameInput = $('#usernameInput')
							var passwordInput = $('#passwordInput')
							var registerDateInput = $('#registerDateInput');
							
							usernameInput.prop('disabled', true)
							usernameInput.val(user.username)
							passwordInput.val(user.password)
							var date = new Date(parseInt(user.registrationDate))
							console.log(date)
							var result = date.format('mm/dd/yyyy')
							console.log(result)
							registerDateInput.text(result)
							
							$('#updateSubmit').on('click', function(event){
								var conf = confirm("Are you sure that you want to change data?")
								if(conf == false){
									return false;
								}else {
								
								var password = passwordInput.val()
								var registrationDate = registerDateInput.val()

								if(password == ""){
									alert("You must enter a password")
									event.preventDefault()
									return false
								}
		
								params = {
									'action' : 'editByUser',
									'id' : id,
									'password' : password,
									'role': 'USER',
									'blockedUser' : 'Active'
								}
								console.log(params)
								
								$.post('UserProfileServlet', params, function(data){
									
									if (data.status == 'unauthenticated') {
										window.location.replace('login.html');
										return;
									}

									if(data.status == "faliure"){
										alert("Something went wrong")
									}
									
									if(data.status == 'success'){
										alert("Successfull update")
										window.location.replace('AdminWindow.html')
										return;
									}
									
								})
								
								event.preventDefault();
								return false;
							}
							})
						}
					})
			
			}
		})
		
	}
	
	getUser()
})