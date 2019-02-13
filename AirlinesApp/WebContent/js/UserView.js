$(document).ready(function() {
	
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

	var userViewTable = $('#userViewTable');
	var usernameFilterInput = $('#usernameFilterInput')
	var role;

	$('#dropdownRole').click(function(event){
		var rolee = $(event.target).text()
		if(rolee != null) {
			role = rolee
			getUsers()

			event.preventDefault()
			return false;
		}
		

	})
	
	
	
	function getUsers() {
		$.get('UserServlet', {'action': 'loggedInUserRole'}, function(data) {

			if (data.status == 'unauthenticated') {
				window.location.replace('login.html');
				return;
			}
			
			if(data.loggedInUserRole == 'ADMIN') {
			
				$.get('UserServlet', {'action' : 'loggedInUserStatus'}, function(data) {

					if (data.status == 'unauthenticated') {
						window.location.replace('login.html');
						return;
					}

					if(data.loggedInUserStatus == true) {
						window.location.replace('AdminWindow.html')
					}
					var usernameFilter = usernameFilterInput.val();
					
					
					params = {
						'usernameFilter' : usernameFilter,
						'role' : role
					}
			
					$.get('UsersViewServlet', params, function(data){
						console.log(data);
						
						if (data.status == 'unauthenticated') {
							window.location.replace('login.html');
							return;
						}
						
						if(data.status == 'success'){
							console.log(data)
							userViewTable.find('tr:gt(0)').remove();
							
							var filteredUsers = data.users

							for(var u in filteredUsers){
								var date = new Date(filteredUsers[u].registrationDate)
								userViewTable.append(
									'<tr>' +
										'<td><a href="UserProfile.html?id=' + filteredUsers[u].id + '">'  + filteredUsers[u].username + '</td>' +
										'<td>' + date.toLocaleDateString() + '</td>' +
										'<td>' + filteredUsers[u].role + '</td>' +		
									'</tr>'
								)
							}
						
						
						}
					})
				})
			}
			else if (data.loggedInUserRole == 'USER'){
				window.location.replace('AdminWindow.html')
			}
		})
	}
	
	usernameFilterInput.on('keyup', function(event){
		getUsers()
		
		event.preventDefault();
		return false;
	})

	
	
	getUsers()
	
});
