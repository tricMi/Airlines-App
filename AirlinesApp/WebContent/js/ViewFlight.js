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
	
	function getFlight() {

				var id = window.location.search.slice(1).split('&')[0].split('=')[1];
				console.log(id);
				$.get('UserServlet', {'action': 'loggedInUserRole'}, function(data) {

					
					if(data.loggedInUserRole == 'ADMIN' || data.loggedInUserRole == 'USER') {
						console.log(data.loggedInUserRole.toString())
						if(data.loggedInUserRole == 'USER') {
							$('#userNav').show()
							$('#adminNav').hide()
							$('#deleteSubmit').hide()
							$('#editSubmit').hide()
							$('#unauthenticatedNav').hide()
							$('#bookedTicketsTable').hide()
							$('#purchasedTicketsTable').hide()
						}else if(data.loggedInUserRole == 'ADMIN'){
							$('#adminNav').show()
							$('#userNav').hide()	
							$('#unauthenticatedNav').hide()
						}
					
						$.get('UserServlet', {'action' : 'loggedInUserStatus'}, function(data) {
		
		
							if(data.loggedInUserStatus == true) {
								$('#deleteSubmit').hide()
								$('#editSubmit').hide()
								$('#bookedTicketsTable').hide()
								$('#purchasedTicketsTable').hide()
								$('#buySubmit').hide()
							}
				
							$.get('FlightsViewServlet', {'id': id}, function(data){
								
								console.log(data);

							

								
								if(data.status == 'success') {	
									
									var flight = data.flight
									var seat = data.seatsAvailable
									$('#flightNumCell').text(flight.flightNum)
									var departureDate = new Date(parseInt(flight.departureTime))
									var resultDepDate = departureDate.format('ddd mmm dd yyyy HH:MM:ss')
									var arrivalDate = new Date(parseInt(flight.arrivalTime))
									var resultArrivalDate = arrivalDate.format('ddd mmm dd yyyy HH:MM:ss')
									$('#departureTimeCell').text(resultDepDate)
									$('#arrivalTimeCell').text(resultArrivalDate)
									$('#departureCell').text(flight.departures.airportName)
									$('#arrivalCell').text(flight.arrivals.airportName)
									$('#seatNumCell').text(flight.seatNum)
									$('#ticketPriceCell').text(flight.ticketPrice)
									$('#availableSeatsCell').text(seat)

									
										
										$('#editSubmit').on('click', function(event){
											window.location.replace('EditFlight.html?id=' + flight.id)
											return;
										})

										
										$('#deleteSubmit').on('click', function(event){
											$('#deleteModal').modal('toggle')

											
											$('#btnClose').on('click', function(event){
												console.log("I CLICKED")
												$('#deleteModal').modal('hide')
											})
		
											
											$('#btnYes').on('click', function(event){
												
												params = {
													'action' : 'delete',
													'id' : id
												}

												console.log("Delete params: " + params)

												$.post('FlightsViewServlet', params, function(data) {

													if (data.status == 'unauthenticated') {
														window.location.replace('login.html');
														return;
													}

													if(data.status == 'success') {
														alert("Flight deleted successfully")
														window.location.replace('AdminWindow.html')
														return
													}
												})
											

												event.preventDefault();
												return false;
											})
										})

										$('#buySubmit').on('click', function(event){
											$('#myModal').modal('toggle')
										})

										$('#btnClose').click(function(event){
											$('#myModal').modal('hide')
										})

										$('#btnRoundtrip').on('click', function(event){
											window.location.replace('TicketPhaseTwo.html?flightId=' + id)
											return
										})

										$('#btnOneWay').on('click', function(event){
											window.location.replace('TicketPhaseThree.html?flightId=' + id + '&roundtripId=0')
										})
									}

							bookedTicketsTable.find('tr:gt(0)').remove()

								var filteredReservations = data.flightReservations
								console.log(filteredPurchases)

								for(var fr in filteredReservations){
									var reservationDate = new Date(filteredReservations[fr].reservationDateTime)
									var roundSeat = filteredReservations[fr].roundtripSeat
									if(roundSeat == null){
										roundSeat = "No Seat"
									}

									bookedTicketsTable.append(
										'<tr>' +
											'<td><a href="Ticket.html?id=' + filteredReservations[fr].id + '">' + reservationDate.toLocaleString() +  '</td>' +
											'<td>' + filteredReservations[fr].departureSeat + '</td>' +
											'<td>' + roundSeat +  '</td>' +
											'<td><a href ="UserProfile.html?id=' + filteredReservations[fr].username.id + '">' + filteredReservations[fr].username.username +  '</td>' +		
										'</tr>'
									)
								}

								purchasedTicketsTable.find('tr:gt(0)').remove()

								var filteredPurchases = data.flightPurchases
							
								for(var pt in filteredPurchases){
									var purchaseDate = new Date(filteredPurchases[pt].purchaseDateTime)
									var roundSeat = filteredPurchases[pt].roundtripSeat
									if(roundSeat == null){
										roundSeat = "No Seat"
									}

									purchasedTicketsTable.append(
										'<tr>' +
											'<td><a href="Ticket.html?id=' + filteredPurchases[pt].id + '">' + purchaseDate.toLocaleString() +  '</td>' +
											'<td>' + filteredPurchases[pt].departureSeat + '</td>' +
											'<td>' + roundSeat +  '</td>' +
											'<td><a href ="UserProfile.html?id=' + filteredPurchases[pt].username.id + '">' + filteredPurchases[pt].username.username +  '</td>' +		
										'</tr>'
									)
										
					}
				})
			})
		} else {
			$('#logoutLink').hide()
			$('#adminNav').hide()
			$('#userNav').hide()
			$('#bookedTicketsTable').hide()
			$('#purchasedTicketsTable').hide()
			$('#deleteSubmit').hide()
			$('#buySubmit').hide()
			$('#editSubmit').hide()
			$('#unauthenticatedNav').show()

			$.get('FlightsViewServlet', {'id': id}, function(data){
								
				console.log(data);

			

				
				if(data.status == 'success') {	
					
					var flight = data.flight
					var seat = data.seatsAvailable
					$('#flightNumCell').text(flight.flightNum)
					var departureDate = new Date(parseInt(flight.departureTime))
					var resultDepDate = departureDate.format('ddd mmm dd yyyy HH:MM:ss')
					var arrivalDate = new Date(parseInt(flight.arrivalTime))
					var resultArrivalDate = arrivalDate.format('ddd mmm dd yyyy HH:MM:ss')
					$('#departureTimeCell').text(resultDepDate)
					$('#arrivalTimeCell').text(resultArrivalDate)
					$('#departureCell').text(flight.departures.airportName)
					$('#arrivalCell').text(flight.arrivals.airportName)
					$('#seatNumCell').text(flight.seatNum)
					$('#ticketPriceCell').text(flight.ticketPrice)
					$('#availableSeatsCell').text(seat)

					
						
					
					}

			
			})

		}
	})


		

	}
	
	getFlight()
})