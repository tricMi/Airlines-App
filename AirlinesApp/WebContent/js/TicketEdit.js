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
    
    var id = window.location.search.slice(1).split('&')[0].split('=')[1];
    console.log(id);
    var departureSeatInput = $('#departureSeatInput')
    var roundtripSeatInput = $('#roundtripSeatInput')
    var firstNameInput = $('#firstNameInput')
    var surnameInput = $('#surnameInput')

    function getTicket(){
        $.get("TicketsViewServlet", {'id' : id}, function(data){

			console.log(data)
			
			if (data.status == 'unauthenticated') {
				window.location.replace('login.html');
				return;
            }

            if(data.status == 'success') {
                var ticket = data.ticket;
                    firstNameInput.val(ticket.name)
                    surnameInput.val(ticket.surname)
                    departureSeatInput.val(ticket.departureSeat)
                    roundtripSeatInput.val(ticket.roundtripSeat)

                    var departureId = ticket.departureFlight.id
                    var roundtripId = ticket.roundtripFlight
                    if(roundtripId == null) {
                        roundtripId = 0
                    }else {
                        roundtripId = ticket.roundtripFlight.id
                    }
                    console.log(ticket + "TIICKET")
                $.get('UserServlet', {'action': 'loggedInUserRole'}, function(data){


                    if (data.status == 'unauthenticated') {
                        window.location.replace('login.html');
                        return;
                    }
    
                    if(data.loggedInUserRole == 'ADMIN' || data.loggedInUserRole == 'USER'){

                        if(data.loggedInUserRole == 'USER'){
                            $('#adminNav').hide()
                        }
                        
                        $.get('UserServlet', {'action' : 'loggedInUserStatus'}, function(data){

                            if (data.status == 'unauthenticated') {
                                window.location.replace('login.html');
                                return;
                            }
                            
                            if(data.loggedInUserStatus == true){
                                window.location.replace('AdminWindow.html')
                                return
                            }else {
                                
                               

                                $.get('FlightsViewServlet', {'id': departureId}, function(data){
                                    console.log(data)
                
                                    if (data.status == 'unauthenticated') {
                                        window.location.replace('login.html');
                                        return;
                                    }
                    
                                    if(data.status == 'success') {
                    
                                        var flight = data.flight
                                        var taken = data.li
                                        
                    
                                        for(i = 1; i < flight.seatNum; i++){
                                            
                                            state = true;
                    
                                            for(j = 0; j < taken.length; j ++) {
                                                if(i == taken[j]){
                                                    
                                                    state = false;
                                                    break;
                                                }
                                            }
                                            if(state == true){
                                                $('#seat').append('<button type = "button" style="background-color:#696464;" value = "'+ i + '"class = "btn" id = "us">' + i + '</button>')
                                            }else{
                                                $('#seat').append('<button type = "button" style="background-color:black;" disabled value = "'+ i + '"class = "btn" id = "us">' + i + '</button>')
                    
                                        }
                                    }
                                    }
                                });
                    
                            
                    
                                
                                if(roundtripId != 0) {
                                $.get('FlightsViewServlet', {'id': roundtripId}, function(data){
                    
                                    
                                    if (data.status == 'unauthenticated') {
                                        window.location.replace('login.html');
                                        return;
                                    }
                    
                                    if(data.status == 'success') {
                    
                                        var flight = data.flight
                                        var taken = data.li
                    
                    
                                        for(i = 1; i < flight.seatNum; i++){
                                            state = true;
                    
                                            for(j = 0; j < taken.length; j ++) {
                                                if(i == taken[j]){
                                                    state = false;
                                                    break;
                                                }
                                            }
                                            if(state == true){
                                                $('#roundtripSeat').append('<button type = "button" style="background-color:#696464;" value = "'+ i + '"class = "btn" id = "rs">' + i + '</button>')
                                            }else{
                                                $('#roundtripSeat').append('<button type = "button" style="background-color:black;" disabled value = "'+ i + '"class = "btn" id = "rs">' + i + '</button>')
                    
                                            }
                                        
                    
                                        }
                                        
                                    }
                                });
                            }

                                $(document).on('click', '#seat #us', function(event){
                                    
                                    var valuee = $(this).val()
                                    console.log(valuee)
                                    document.getElementById('departureSeatInput').value = valuee
                                })
                            
                    
                                $(document).on('click', '#roundtripSeat #rs', function(event){
                                   
                                    var valuee = $(this).val()
                                    console.log(valuee)
                                     document.getElementById('roundtripSeatInput').value = valuee
                                })

                                departureSeatInput.prop('disabled', true)
                                roundtripSeatInput.prop('disabled', true)

                                $('#btnUpdate').on('click', function(event){
                                    confirm("Are you sure that you want to save changes?")
                                    
                                    
                                    var departureSeat = departureSeatInput.val()
                                    var roundtripSeat = roundtripSeatInput.val()
                                    var firstName = firstNameInput.val()
                                    var surname = surnameInput.val()

                                    if(departureSeat == "") {
                                        alert('You must enter a value')
                                        event.preventDefault()
                                        return false
                                    }
    
    
                                    if(firstName = "") {
                                        alert('You must enter a value')
                                        event.preventDefault()
                                        return false
                                    }

                                    if(surname = ""){
                                        alert('You must enter a value')
                                        event.preventDefault()
                                        return false
                                    }
    
                                    params = {
                                        'action': 'edit',
                                        'id' : id,
                                        'departureSeat' : departureSeat,
                                        'roundtripSeat' : roundtripSeat,
                                        'firstName' : firstName,
                                        'surname' : surname
                                    }
                                    

                                    $.post('TicketsServlet', params, function(event){

                                        if (data.status == 'unauthenticated') {
                                            window.location.replace('login.html');
                                            return;
                                        }

                                        if(data.status == 'failure'){
                                            alert("Something went wrong")
                                        }
                                        
                                        if(data.status == 'success'){
                                            alert("Successful update")
                                            console.log("you shall not pass")
                                            window.location.replace('AdminWindow.html')
                                            return;
                                        }
                                    })
                                    
                                })
                               

                            }
                        })
                    }
                })
            }
        })
    }

    getTicket()

})