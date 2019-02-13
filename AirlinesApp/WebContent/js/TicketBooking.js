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

    var flightsTable = $('#flightsTable')
    var roundtripFlightsTable = $('#roundtripFlightsTable')
    

    function getFlights(){
        $.get('UserServlet', {'action': 'loggedInUserRole'}, function(data) {

            if (data.status == 'unauthenticated') {
                window.location.replace('login.html');
                return;
            }

			if(data.loggedInUserRole == 'USER' || data.loggedInUserRole == 'ADMIN') {
                console.log(data.loggedInUserRole.toString() + "LOGGED IN USER")

                if(data.loggedInUserRole == 'USER'){
                    $('#adminNav').hide()
                }
                
                $.get('TicketsServlet', function(data){
                    console.log(data)

                    if (data.status == 'unauthenticated') {
                        window.location.replace('login.html');
                        return;
                    }

                    if(data.status == 'success'){
                        console.log(data)

                        flightsTable.find('tr:gt(0)').remove()

                        var filteredFlights = data.currentDateFlight

                        for(var f in filteredFlights){
                            var date = new Date(filteredFlights[f].departureTime)
                            console.log(date)
                            flightsTable.append(
                                '<tr>' +
									'<td>' + filteredFlights[f].flightNum + '</td>' +
									'<td>' + filteredFlights[f].departures.airportName + '</td>' +
									'<td>' + date.toLocaleString() + '</td>' +
									'<td>' + '<button type="submit" class="btn btn-dark" id ="bookTicket" style="width:200px" value="' + filteredFlights[f].id + '">Book/Purchase Ticket</button>' + '</td>' +
									
                                '</tr>'
                                
                               
                            )

                            
                        }

                        flightsTable.on('click', '#bookTicket', function(event){
                            var id = $(this).val();
                    
                            if(id != null) {
                    
                            $.get('FlightsViewServlet', {'id': id}, function(data){

                                if (data.status == 'unauthenticated') {
                                    window.location.replace('login.html');
                                    return;
                                }
                                
                                if(data.status == 'success') {
                    
                                    var flight = data.flight
                    
                                    $('#flightNumInput').prop('disabled', true)
                                    $('#departureInput').prop('disabled', true)
                                    $('#departureTimeInput').prop('disabled', true)
                                    var departureDateTime = new Date(flight.departureTime)
                                    $('#flightNumInput').val(flight.flightNum)
                                    $('#departureInput').val(flight.departures.airportName)
                                    $('#departureTimeInput').val(departureDateTime.toLocaleString())
                    
                                  
                                   
                    
                                    
                    
                                }
                               
                            })
                    
                        }

                        $('#btnNext').on('click', function(event) {
                                  
                                   
                            var flightTypeValue = $("input[name='flightType']:checked").val();
                            console.log(flightTypeValue)
        
                            if(flightTypeValue == 'roundtrip'){
                                console.log(flightTypeValue)
                                window.location.replace('TicketPhaseTwo.html?flightId=' + id)
                                return
                            }else if(flightTypeValue == 'oneWay'){
                                console.log(flightTypeValue)
                                window.location.replace('TicketPhaseThree.html?flightId=' + id + '&roundtripId=0')
                                return
                            }else if(flightTypeValue == null) {
                                alert("You must select a type")
                                event.preventDefault()
                                return false
                            }

                             

                            
                           
                        })
                    
                    
                        })

                        $('#btnNext').on('click', function(event){
                           
                              if($('#flightNumInput').val() == ""){
                                alert("You must select flight")
                                event.preventDefault()
                                return false
                            }

                            if($('#departureInput').val() == ""){
                                alert("You must select flight")
                                event.preventDefault()
                                return false
                            }

                            if($('#departureTimeInput').val() == ""){
                                alert("You must select flight")
                                event.preventDefault()
                                return false
                            }
                             
                        })

                        $('#btnCancel').on('click', function(event){
                            var conf = confirm("Are tou sure that you want to cancel purchase/reservation? ")
                            if(conf == false){
                                return false
                            }else
                                window.location.replace('AdminWindow.html')
                                return
                        })

                        
                    }
                
                })
            }
        })
    }




    function getRoundtripFlights(){

        var flightId = window.location.search.slice(1).split('&')[0].split('=')[1];
		console.log("This is it " + flightId);

        $.get('FlightsViewServlet', {'id': flightId}, function(data){

            if (data.status == 'unauthenticated') {
                window.location.replace('login.html');
                return;
            }

            if(data.status == 'success') {

                var flight = data.flight

                $('#flightNumInputSecondPhase').prop('disabled', true)
                $('#departureInputSecondPhase').prop('disabled', true)
                $('#departureTimeInputSecondPhase').prop('disabled', true)
                var departureDateTime = new Date(flight.departureTime)
                $('#flightNumInputSecondPhase').val(flight.flightNum)
                $('#departureInputSecondPhase').val(flight.departures.airportName)
                $('#departureTimeInputSecondPhase').val(departureDateTime.toLocaleString())


                $.get('UserServlet', {'action': 'loggedInUserRole'}, function(data) {
                    console.log(data)

                    if (data.status == 'unauthenticated') {
                        window.location.replace('login.html');
                        return;
                    }

                    if(data.loggedInUserRole == 'USER' || data.loggedInUserRole == 'ADMIN') {
        
                        if(data.loggedInUserRole == 'USER'){
                            $('#adminNav').hide()
                        }
                        
                        $.get('FlightsViewServlet', {'id': flightId}, function(data){
                            console.log(data)
        
                            if (data.status == 'unauthenticated') {
                                window.location.replace('login.html');
                                return;
                            }
        
                            if(data.status == 'success'){
                                console.log(data)
        
                                roundtripFlightsTable.find('tr:gt(0)').remove()
        
                                var filteredFlights = data.roundtripFlights
                                console.log("This ones " + filteredFlights)
        
                                for(var f in filteredFlights){
                                    var date = new Date(filteredFlights[f].departureTime)
                                    console.log(date)
                                    roundtripFlightsTable.append(
                                        '<tr>' +
                                            '<td>' + filteredFlights[f].flightNum + '</td>' +
                                            '<td>' + filteredFlights[f].departures.airportName + '</td>' +
                                            '<td>' + date.toLocaleString() + '</td>' +
                                            '<td>' + '<button type="submit" class="btn btn-dark" id ="bookTicket" style="width:200px" value="' + filteredFlights[f].id + '">Book/Purchase Ticket</button>' + '</td>' +
                                            
                                        '</tr>'
                                        
                                       
                                    )
        
                                    
                                }

                                roundtripFlightsTable.on('click', '#bookTicket', function(event){
                                    var id = $(this).val()
                            
                                    $.get('FlightsViewServlet', {'id': id}, function(data){

                                        if (data.status == 'unauthenticated') {
                                            window.location.replace('login.html');
                                            return;
                                        }
                                        
                                        if(data.status == 'success') {
                            
                                            var flight = data.flight
                            
                                            $('#roundtripFlightNumInput').prop('disabled', true)
                                            $('#roundtripDepartureInput').prop('disabled', true)
                                            $('#roundtripDepartureTimeInput').prop('disabled', true)
                                            var departureDateTime = new Date(flight.departureTime)
                                            $('#roundtripFlightNumInput').val(flight.flightNum)
                                            $('#roundtripDepartureInput').val(flight.departures.airportName)
                                            $('#roundtripDepartureTimeInput').val(departureDateTime.toLocaleString())
                            
                                           
                                          
                                            $('#btnNext').on('click', function(event) {
                                                window.location.replace('TicketPhaseThree.html?flightId=' + flightId + '&roundtripId=' + flight.id)
                                                return
                                            })
                                               
                                           
                                            
                                        }
                                    })
                                })
                                
        
                                
                            }
                        
                        })

                       

                        $('#buttonPrevious').on('click', function(event){
                            console.log('buttton')
                            window.location.replace('TicketPhaseOne.html?flightId=' + flightId)
                            event.preventDefault()
                            return false
                        })

                        $('#btnCancel').on('click', function(event){
                            var conf = confirm("Are tou sure that you want to cancel purchase/reservation? ")
                            if(conf == false){
                                return false
                            }else
                                window.location.replace('AdminWindow.html')
                                return
                        
                        })

                        $('#btnNext').on('click', function(event){

                            if($('#roundtripFlightNumInput').val() == ""){
                              alert("You must select flight")
                              event.preventDefault()
                              return false
                          }
                
                          if($('#roundtripDepartureInput').val() == ""){
                              alert("You must select flight")
                              event.preventDefault()
                              return false
                          }
                
                          if($('#roundtripDepartureTimeInput').val() == ""){
                              alert("You must select flight")
                              event.preventDefault()
                              return false
                          }
                           
                      })
                    }

                   
                })
            }

        })

      

    }

    var firstNameInput = $("#firstNameInput")
    var surnameInput = $('#surnameInput')
    var departureSeatInput = $('#departureSeatInput')
    var roundtripSeatInput = $('#roundtripSeatInput')

    var flight;
    var flightRound;
    function getAllTicketData(){
        
        var flightId = window.location.search.slice(1).split('&')[0].split('=')[1];
        console.log("This is flight id " + flightId);
        var roundtripId = window.location.search.slice(1).split('&')[1].split('=')[1];
        console.log("This is roundtrip id " + roundtripId);
        $.get('UserServlet', {'action': 'loggedInUserRole'}, function(data) {
            console.log(data)

            if (data.status == 'unauthenticated') {
                window.location.replace('login.html');
                return;
            }

            if(data.loggedInUserRole == 'USER' || data.loggedInUserRole == 'ADMIN') {

                if(data.loggedInUserRole == 'USER'){
                    $('#adminNav').hide()
                }

            
           
            
       
            $.get('FlightsViewServlet', {'id': flightId}, function(data){

                
                if (data.status == 'unauthenticated') {
                    window.location.replace('login.html');
                    return;
                }

                if(data.status == 'success') {

                    flight = data.flight

                    $('#flightNumInputFinal').prop('disabled', true)
                    $('#departureInputFinal').prop('disabled', true)
                    $('#departureTimeInputFinal').prop('disabled', true)
                    var departureDateTime = new Date(flight.departureTime)
                    $('#flightNumInputFinal').val(flight.flightNum)
                    $('#departureInputFinal').val(flight.departures.airportName)
                    $('#departureTimeInputFinal').val(departureDateTime.toLocaleString())
                  //  $('#ticketPriceInput').append(flight.ticketPrice)
                 //   $('#ticketPriceInput').val(flight.ticketPrice)

                 document.getElementById('ticketPriceInput').value = parseInt(flight.ticketPrice)
                 console.log("FIRST VALUE" + flight.ticketPrice)
               
                   
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
                            $('#seat').append('<button type = "button" style="background-color:#60634e;" value = "'+ i + '"class = "btn" id = "us">' + i + '</button>')
                        }else{
                            $('#seat').append('<button type = "button" style="background-color:black;" disabled value = "'+ i + '"class = "btn" id = "us">' + i + '</button>')

                    }
                }
                }

                var elem = document.getElementById('ticketPriceInput');
                var old  = elem.value;
                console.log(elem.value + "VAAALUE ")
                elem.value = parseInt(old) + parseInt(flightRound.ticketPrice)
            });

        
           
        
          
         

            
            if(roundtripId != '0') {
                 

            $.get('FlightsViewServlet', {'id': roundtripId}, function(data){

                
                if (data.status == 'unauthenticated') {
                    window.location.replace('login.html');
                    return;
                }

                if(data.status == 'success') {

                    flightRound = data.flight
                    

                    $('#roundtripFlightNumInputFinal').prop('disabled', true)
                    $('#roundtripDepartureInputFinal').prop('disabled', true)
                    $('#roundtripDepartureTimeInputFinal').prop('disabled', true)
                    var departureDateTime = new Date(flightRound.departureTime)
                    $('#roundtripFlightNumInputFinal').val(flightRound.flightNum)
                    $('#roundtripDepartureInputFinal').val(flightRound.departures.airportName)
                    $('#roundtripDepartureTimeInputFinal').val(departureDateTime.toLocaleString())

                    var taken = data.li


                    for(i = 1; i < flightRound.seatNum; i++){
            
                        state = true;

                        for(j = 0; j < taken.length; j ++) {
                            if(i == taken[j]){
                                state = false;
                                break;
                            }
                        }
                        if(state == true){
                            $('#roundtripSeat').append('<button type = "button" style="background-color:#60634e;" value = "'+ i + '"class = "btn" id = "rs">' + i + '</button>')
                        }else{
                            $('#roundtripSeat').append('<button type = "button" style="background-color:black;" disabled value = "'+ i + '"class = "btn" id = "rs">' + i + '</button>')

                        }

                    }
                  
                }

                var elem = document.getElementById('ticketPriceInput');
                var old  = elem.value;
                console.log(elem.value + "VAAALUE ")
                elem.value = parseInt(old) + parseInt(flightRound.ticketPrice)
                console.log(flightRound.ticketPrice + "VAAALUE ")
     
               
            });

         

           
        }

       
            $('#departureSeatInput').prop('disabled', true)
            $('#roundtripSeatInput').prop('disabled', true)
            $('#totalPriceInput').prop('disabled', true)
           

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



            $('#btnReservation').on('click', function(event){
     
                var conf = confirm("Are you sure that you want to book ticket?")
                if(conf == false){
                    return false;
                }else {

                var firstName = firstNameInput.val()
                var surname = surnameInput.val()
                var departureSeat = departureSeatInput.val();
                var roundtripSeat = roundtripSeatInput.val();

                if(departureSeat == ""){
                    alert("You must select departure Seat")

                    event.preventDefault()
                    return false
                }
               
                if(firstName == ""){
                    alert("You must enter a name")

                    event.preventDefault()
                    return false
                }

                if(surname == ""){
                    alert("You must enter a surname")

                    event.preventDefault()
                    return false
                }

                if(roundtripId != '0' && roundtripSeat == ""){
                    
                    alert("You must select roundtrip Seat")

                    event.preventDefault()
                    return false
                
                }

                params = {
                    'action' : 'booking',
                    'flightId' : flightId,
                    'roundtripId' : roundtripId,
                    'departureSeat' : departureSeat,
                    'roundtripSeat' : roundtripSeat,
                    'firstName' : firstName, 
                    'surname' : surname,
                }

                $.post('TicketsServlet', params, function(data){
                    if (data.status == 'unauthenticated') {
                        window.location.replace('login.html');
                        return;
                    }

                    if (data.status == 'success') {
                        alert('Successful reservation')
                        window.location.replace('AdminWindow.html');
                    }
                })
            }
        
            })

            $('#btnPurchase').on('click', function(event){
                var conf = confirm("Are you sure that you want to purchase ticket?")
                if(conf == false){
                    return false;
                }else {

                var firstName = firstNameInput.val()
                var surname = surnameInput.val()
                var departureSeat = departureSeatInput.val();
                var roundtripSeat = roundtripSeatInput.val();

                if(departureSeat == ""){
                    alert("You must select departure Seat")

                    event.preventDefault()
                    return false
                }

                if(firstName == ""){
                    alert("You must enter a name")

                    event.preventDefault()
                    return false
                }

                if(surname == ""){
                    alert("You must enter a surname")

                    event.preventDefault()
                    return false
                }

                if(roundtripId != '0' && roundtripSeat == ""){
                    
                    alert("You must select roundtrip Seat")

                    event.preventDefault()
                    return false
                
                }

               

                params = {
                    'action' : 'purchase',
                    'flightId' : flightId,
                    'roundtripId' : roundtripId,
                    'departureSeat' : departureSeat,
                    'roundtripSeat' : roundtripSeat,
                    'firstName' : firstName, 
                    'surname' : surname,
                }

                $.post('TicketsServlet', params, function(data){

                    if (data.status == 'unauthenticated') {
                        window.location.replace('login.html');
                        return;
                    }

                    if (data.status == 'success') {
                        alert('Successful purchase')
                        window.location.replace('AdminWindow.html');
                    }
                })
            }
            })
        

            $('#btnCancel').on('click', function(event){
                var conf = confirm("Are tou sure that you want to cancel purchase/reservation? ")
                if(conf == false){
                    return false
                }else
                    window.location.replace('AdminWindow.html')
                    return
            })
        
            $('#btnPrevious').on('click', function(event){
                if(roundtripId != '0'){
                    window.location.replace('TicketPhaseTwo.html?flightId=' + flightId)
                }else{
                    window.location.replace('TicketPhaseOne.html')
                }
                return
            })

           
        }
    

       
    })

   

    }
    
   
    var phase = window.location.href;
    if(phase.includes("TicketPhaseOne.html")){
        getFlights()
    }else if(phase.includes("TicketPhaseTwo.html")) {
        getRoundtripFlights()
    }else if(phase.includes("TicketPhaseThree.html")){
        getAllTicketData()
    }
    

})