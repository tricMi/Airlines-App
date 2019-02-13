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
				$('#deleteSubmit').hide()
				$.get('UserServlet', {'action' : 'loggedInUserStatus'}, function(data) {
					if(data.loggedInUserStatus == true) {
						$('#editSubmit').hide()
						$('#deleteSubmit').hide()
					}
					
					$.get('LoggedInUserServlet',function(data){
						
						console.log(data);
						
						if (data.status == 'unauthenticated') {
							window.location.replace('login.html');
							return;
						}
						
						if(data.status == 'success') {
							var user = data.user
							console.log("djsajdsads" + user)
							$('#usernameCell').text(user.username)
							var registerDate = new Date(user.registrationDate)
							$('#registerDateCell').text(registerDate)
							$('#roleCell').text(user.role)
							$('#blockedUserCell').text(user.blockedUser)
							var id = user.id
							
							$('#editSubmit').on('click', function(event){
								window.location.replace('UserEdit.html?id=' + id)
								return;
							})


							
					}

					bookedTicketsTable.find('tr:gt(0)').remove()

					var filteredReservations = data.reservedTickets

					for(var fr in filteredReservations){
						var reservationDate = new Date(filteredReservations[fr].reservationDateTime)

						bookedTicketsTable.append(
							'<tr>' +
								'<td><a href="Ticket.html?id=' + filteredReservations[fr].id + '">' + reservationDate.toLocaleString() +  '</td>' +
							'</tr>'
						)
					}

					purchasedTicketsTable.find('tr:gt(0)').remove()

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
				$.get('UserServlet', {'action' : 'loggedInUserStatus'}, function(data) {
					$('#adminNav').hide()
					console.log('dataaaaaaaaaaaaaaaaaaaaaa' + data.loggedInUserRole)
					if(data.loggedInUserStatus == false) {
						$('#deleteSubmit').hide()

					}else{
						$('#deleteSubmit').hide()
						$('#editSubmit').hide()
					}

				
					$.get('LoggedInUserServlet',function(data){
						
						console.log(data);
						
						if (data.status == 'unauthenticated') {
							window.location.replace('login.html');
							return;
						}
						
						if(data.status == 'success') {
							var user = data.user
							console.log("djsajdsads" + user)
							$('#usernameCell').text(user.username)
							var registerDate = new Date(user.registrationDate)
							$('#registerDateCell').text(registerDate)
							$('#roleCell').text(user.role)
							$('#blockedUserCell').text(user.blockedUser)

							var id = user.id
							console.log("IDDDD " + id)
							
							$('#editSubmit').on('click', function(event){
								window.location.replace('UserEdit.html?id=' + id)
								return;
							})

							bookedTicketsTable.find('tr:gt(0)').remove()

							var filteredReservations = data.reservedTickets

							for(var fr in filteredReservations){
								var reservationDate = new Date(filteredReservations[fr].reservationDateTime)

								bookedTicketsTable.append(
									'<tr>' +
										'<td><a href="Ticket.html?id=' + filteredReservations[fr].id + '">' + reservationDate.toLocaleString() +  '</td>' +
									'</tr>'
								)
							}

							purchasedTicketsTable.find('tr:gt(0)').remove()

							var filteredPurchases = data.purchasedTickets
						
							for(var pt in filteredPurchases){
								var purchaseDate = new Date(filteredPurchases[pt].purchaseDateTime)

								purchasedTicketsTable.append(
									'<tr>' +
										'<td><a href="Ticket.html?id=' + filteredPurchases[pt].id + '">' + purchaseDate.toLocaleString() +  '</td>' +	
									'</tr>'
								)
							}
						}
					})
				
			})
			}
		})
	}
	
	getUsers()
})