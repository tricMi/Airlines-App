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

	var bookedTicketsTable = $('#bookedTicketsTable')
	var purchasedTicketsTable = $('#purchasedTicketsTable')
	
	function getUsers() {
		$.get('UserServlet', {'action': 'loggedInUserRole'}, function(data) {

			if (data.status == 'unauthenticated') {
				window.location.replace('login.html');
				return;
			}

			if(data.loggedInUserRole == 'ADMIN') {
				$.get('UserServlet', {'action' : 'loggedInUserStatus'}, function(data) {
					if(data.loggedInUserStatus == true) {
						$('#editSubmit').hide()
						$('#deleteSubmit').hide()
					}


					var id = window.location.search.slice(1).split('&')[0].split('=')[1];
					console.log(id);
					
					$.get('UserProfileServlet', {'id': id}, function(data){
						
						console.log(data);
						
						if (data.status == 'unauthenticated') {
							window.location.replace('login.html');
							return;
						}
						
						if(data.status == 'success') {
							var user = data.user
							$('#usernameCell').text(user.username)
							var registerDate = new Date(user.registrationDate)
							$('#registerDateCell').text(registerDate)
							$('#roleCell').text(user.role)
							$('#blockedUserCell').text(user.blockedUser)
							
							
							$('#editSubmit').on('click', function(event){
								window.location.replace('UserEdit.html?id=' + id)
								return;
							})

							$('#deleteSubmit').on('click', function(event){
								$('#myModal').modal('toggle')

								$('#btnClose').on('click', function(event){
									console.log("I CLICKED")
										$('#myModal').modal('hide')
								})

								$('#btnYes').on('click', function(event){
									params = {
										'action' : 'delete',
										'id' : id
									}
			
									$.post('UserProfileServlet', params, function(data){
			
										console.log("deleeete ddata" + params)
			
										if (data.status == 'unauthenticated') {
											window.location.replace('login.html');
											return;
										}

										if(data.status == 'failure'){
											alert("Something went wrong")
										}
										
										if(data.status == 'success'){
											alert("User deleted successfully")
											window.location.replace('AdminWindow.html')
											return;
										}
			
									})
								})
								
								
						})
					}

					bookedTicketsTable.find('tr:gt(1)').remove()

					var filteredReservations = data.reservedTickets

					for(var fr in filteredReservations){
						var reservationDate = new Date(filteredReservations[fr].reservationDateTime)

						bookedTicketsTable.append(
							'<tr>' +
								'<td><a href="Ticket.html?id=' + filteredReservations[fr].id + '">' + reservationDate.toLocaleString() +  '</td>' +
							'</tr>'
						)
					}

					purchasedTicketsTable.find('tr:gt(1)').remove()

					var filteredPurchases = data.purchasedTickets
				
					for(var pt in filteredPurchases){
						var purchaseDate = new Date(filteredPurchases[pt].purchaseDateTime)

						purchasedTicketsTable.append(
							'<tr>' +
								'<td><a href="Ticket.html?id=' + filteredPurchases[pt].id + '">' + purchaseDate.toLocaleString() +  '</td>' +	
							'</tr>'
						)
					
					}

					
				})

			
			
		})
			}else if(data.loggedInUserRole == 'USER') {
				window.location.replace('AdminWindow.html')
				return
			
			 }
		})
	}
	
	getUsers()
})