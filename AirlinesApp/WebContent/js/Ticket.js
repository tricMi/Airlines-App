$(document).ready(function(event){

    // var seatTable = $('#seat')

    // for(i =0; i < 10; i++){
    //     seatTable.append(
    //         '<button></button>'
    //     )
    // }

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


    var reservedTickets = $("#bookedTicketsTable")
    var purchasedTickets = $('#purchasedTicketsTable')

    function getTickets() {

        $.get('UserServlet', {'action': 'loggedInUserRole'}, function(data) {

            if (data.status == 'unauthenticated') {
                window.location.replace('login.html');
                return;
            }

            if(data.loggedInUserRole == 'ADMIN') {
            
                $.get('TicketsServlet', function(data){

                    if(data.status == 'success'){
                        console.log("dataaaaaaa" + data)
                        reservedTickets.find('tr:gt(0)').remove();
                            
                        var filteredReservations = data.reservedTickets

                        var filteredReservations = data.reservedTickets

                        for(var fr in filteredReservations){
                            var reservationDate = new Date(filteredReservations[fr].reservationDateTime)
                            var roundFlight = filteredReservations[fr].roundtripFlight
                           

                            if(roundFlight != null) {
                                reservedTickets.append(
                                    '<tr>' +
                                        '<td><a href="ViewFlight.html?id=' + filteredReservations[fr].departureFlight.id + '" style=color:white;text-decoration:none;>' + filteredReservations[fr].departureFlight.flightNum + '</td>' +
                                        '<td><a href="ViewFlight.html?id=' + roundFlight.id + '">' + filteredReservations[fr].roundtripFlight.flightNum + '</td>' +
                                        '<td>' + filteredReservations[fr].departureSeat + '</td>' +
                                        '<td>' + filteredReservations[fr].roundtripSeat +  '</td>' +
                                        '<td><a href="Ticket.html?id=' + filteredReservations[fr].id + '">' + reservationDate.toLocaleString() +  '</td>' +
                                        '<td><a href="UserProfile.html?id=' + filteredReservations[fr].username.id + '">' + filteredReservations[fr].username.username +  '</td>' +
                                        '<td>' + filteredReservations[fr].name +  '</td>' +
                                        '<td>' + filteredReservations[fr].surname +  '</td>' +
                                        
                                    '</tr>'
                                )
                          }else {
                            reservedTickets.append(
                                '<tr>' +
                                    '<td><a href="ViewFlight.html?id=' + filteredReservations[fr].departureFlight.id + '">' + filteredReservations[fr].departureFlight.flightNum + '</td>' +
                                    '<td>' + "/" + '</td>' +
                                    '<td>' + filteredReservations[fr].departureSeat + '</td>' +
                                    '<td>' + "/" +  '</td>' +
                                    '<td><a href="Ticket.html?id=' + filteredReservations[fr].id + '">' + reservationDate.toLocaleString() +  '</td>' +
                                    '<td><a href="UserProfile.html?id=' + filteredReservations[fr].username.id + '">' + filteredReservations[fr].username.username +  '</td>' +
                                    '<td>' + filteredReservations[fr].name +  '</td>' +
                                    '<td>' + filteredReservations[fr].surname +  '</td>' +
                                    
                                '</tr>'
                            )
                          }
                        }

                        purchasedTickets.find('tr:gt(0)').remove();

                        var filteredPurchases = data.purchasedTickets
                        
                            for(var pt in filteredPurchases){
                                var purchaseDate = new Date(filteredPurchases[pt].purchaseDateTime)
                                var roundFlight = filteredPurchases[pt].roundtripFlight

                                if(roundFlight != null) {
                                    purchasedTickets.append(
                                        '<tr>' +
                                            '<td><a href="ViewFlight.html?id=' + filteredPurchases[pt].departureFlight.id +  '">' + filteredPurchases[pt].departureFlight.flightNum + '</td>' +
                                            '<td><a href="ViewFlight.html?id=' + roundFlight.id + '"id="flightLink">' + filteredPurchases[pt].roundtripFlight.flightNum + '</td>' +
                                            '<td>' + filteredPurchases[pt].departureSeat + '</td>' +
                                            '<td>' + filteredPurchases[pt].roundtripSeat +  '</td>' +
                                            '<td><a href="Ticket.html?id=' + filteredPurchases[pt].id + '">' + purchaseDate.toLocaleString() +  '</td>' +
                                            '<td><a href="UserProfile.html?id=' + filteredPurchases[pt].username.id + '">' + filteredPurchases[pt].username.username +  '</td>' +
                                            '<td>' + filteredPurchases[pt].name +  '</td>' +
                                            '<td>' + filteredPurchases[pt].surname +  '</td>' +
                                            
                                        '</tr>'
                                    )
                                }else {
                                    purchasedTickets.append(
                                        '<tr>' +
                                            '<td><a href="ViewFlight.html?id=' + filteredPurchases[pt].departureFlight.id +  '">' + filteredPurchases[pt].departureFlight.flightNum + '</td>' +
                                            '<td>' + '/' + '</td>' +
                                            '<td>' + filteredPurchases[pt].departureSeat + '</td>' +
                                            '<td>' + "/" +  '</td>' +
                                            '<td><a href="Ticket.html?id=' + filteredPurchases[pt].id + '">' + purchaseDate.toLocaleString() +  '</td>' +
                                            '<td><a href="UserProfile.html?id=' + filteredPurchases[pt].username.id + '">' + filteredPurchases[pt].username.username +  '</td>' +
                                            '<td>' + filteredPurchases[pt].name +  '</td>' +
                                            '<td>' + filteredPurchases[pt].surname +  '</td>' +
                                            
                                        '</tr>'
                                    )
                                }
                            }
                    }

                })
                

            }
        })
    }

    getTickets()
})