$(document).ready(function(event){

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
    
    var date = new Date()
    var day = date.getDate()
    var month = date.getMonth() + 1;
    var year = date.getFullYear()

    var h = date.getHours()
    var m = date.getMinutes()
    var arrivalTime = h + ":" + m
    var arrivalDate = year + "-" + month + "-" + day
    var datetime = arrivalDate + " " + arrivalTime


    function getTicket() {
		$.get('UserServlet', {'action': 'loggedInUserRole'}, function(data) {

			if (data.status == 'unauthenticated') {
				window.location.replace('login.html');
				return;
            }

            if(data.loggedInUserRole == 'ADMIN' || data.loggedInUserRole == 'USER'){

                if(data.loggedInUserRole == 'USER'){
                    $('#userView').hide()
                }

                $.get('UserServlet', {'action': 'loggedInUserStatus'}, function(data) {

                   
                    if (data.status == 'unauthenticated') {
                        window.location.replace('login.html');
                        return;
                    }

                    if(data.loggedInUserStatus == true){
                        $('#deleteSubmit').hide()
                        $('#editSubmit').hide()
                        $('#buySubmit').hide()
                    }


                        var id = window.location.search.slice(1).split('&')[0].split('=')[1];
                        console.log(id);
                        
                        $.get('TicketsViewServlet', {'id' : id}, function(data){

                            console.log("Tiiicket data " + data)

                            if (data.status == 'unauthenticated') {
                                window.location.replace('login.html');
                                return;
                            }
                            
                            if(data.status == 'success'){
                                var ticket = data.ticket

                                if(ticket.purchaseDateTime != null){
                                    $('#deleteSubmit').hide()
                                    $('#buySubmit').hide()
                                    $('#editSubmit').hide()
                                }

                                console.log(ticket)

                                $('#departureFlightCell').append('<a href = "ViewFlight.html?id=' + ticket.departureFlight.id + '">' + ticket.departureFlight.flightNum + '</a>')
                                var roundFlight = ticket.roundtripFlight

                                if(roundFlight == null) {
                                    roundFlight = "/"
                                    $('#roundtripFlightCell').text(roundFlight)
                                }else {
                                    roundFlight =  ticket.roundtripFlight.flightNum  
                                    $('#roundtripFlightCell').append('<a href = "ViewFlight.html?id=' + ticket.roundtripFlight.id + '">' + roundFlight + '</a>')
                                }

                                var roundSeat = ticket.roundtripSeat
                                if(roundSeat == 0){
                                    roundSeat = "/"
                                }else {
                                    roundSeat = ticket.roundtripSeat
                                }
                                
                                $('#departureSeatCell').text(ticket.departureSeat)
                                $('#roundtripSeatCell').text(roundSeat)

                                var reservationDate = new Date(ticket.reservationDateTime)
                                if(ticket.reservationDateTime == null) {
                                    reservationDate = "No Reservation"
                                }else {
                                    reservationDate = new Date(ticket.reservationDateTime)
                                }

                                $('#reservationDateCell').text(reservationDate.toLocaleString())
                                var purchaseDate = new Date(ticket.purchaseDateTime)
                                if(ticket.purchaseDateTime == null) {
                                    purchaseDate = "No purchase"
                                }else {
                                    purchaseDate = new Date(ticket.purchaseDateTime)
                                }

                                $('#purchaseDateCell').text(purchaseDate.toLocaleString())
                                
                                if(data.loggedInUserRole == 'ADMIN'){
                                    
                                     $('#usernameCell').append('<a href = "UserProfile.html?id=' +ticket.username.id+  '"> '+ ticket.username.username + '</a>')
                                }else {
                                    
                                    $('#usernameCell').append('<a href = "LoggedInUser.html">'+ ticket.username.username + '</a>')
                                }
                                
                                $('#nameCell').text(ticket.name)
                                $('#surnameCell').text(ticket.surname)

                                var ticketPrice = ticket.roundtripFlight
                                
                                if(ticketPrice == null) {
                                    ticketPrice = 0
                                }else {
                                    ticketPrice = ticket.roundtripFlight.ticketPrice
                                    console.log(ticketPrice)
                                }
                        
                                $('#ticketPriceCell').text(ticket.departureFlight.ticketPrice + ticketPrice)

                                $('#buySubmit').on('click', function(event){
                                    var conf = confirm("Are you sure that you want to buy a ticket?")
                                    if(conf == false){
                                        return false;
                                    }else {

                                        params = {
                                            'action' : 'buyReservation',
                                            'datetime' : datetime,
                                            'id' : id
                                        }

                                        $.post('TicketsServlet', params, function(data){
                                            console.log("updateeee ddata" + params)
                    
                                                if (data.status == 'unauthenticated') {
                                                    window.location.replace('login.html');
                                                    return;
                                                }

                                                if(data.status == 'failure'){
                                                    alert("Something went wrong")
                                                }
                                                
                                                if(data.status == 'success'){
                                                    alert("Successful purchase")
                                                    window.location.replace('AdminWindow.html')
                                                    return;
                                                }
                                        })
                                    }
                                })

                                $('#deleteSubmit').on('click', function(data){

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

                                        $.post('TicketsServlet', params, function(data){
                                            console.log("deleteee ddata" + params)
                    
                                                if (data.status == 'unauthenticated') {
                                                    window.location.replace('login.html');
                                                    return;
                                                }
                                                
                                                if(data.status == 'success'){
                                                    alert("Successful delete")
                                                    window.location.replace('AdminWindow.html')
                                                    return;
                                                }
                                        })
                                    })
                                })

                                $('#editSubmit').on('click', function(event){
                                    window.location.replace('EditTicket.html?id=' + id);
                                    return
                                })
                            }
                        })
                    
                    
                })
            
                
            }
        })
    }
    
    getTicket()

})