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
    
	
	function getFlight() {
        $.get('UserServlet', {'action': 'loggedInUserRole'}, function(data){

            if (data.status == 'unauthenticated') {
                window.location.replace('login.html');
                return;
            }
            
            if(data.loggedInUserRole == 'ADMIN'){

                $.get('UserServlet', {'action': 'loggedInUserStatus'}, function(data){

                    if (data.status == 'unauthenticated') {
                        window.location.replace('login.html');
                        return;
                    }
        
                    if(data.loggedInUserStatus == false){
                        $.get("FlightsViewServlet", {'id' : id}, function(data){
                            console.log(data)
                            
                            if (data.status == 'unauthenticated') {
                                window.location.replace('login.html');
                                return;
                            }
                            
                            if(data.status == 'success'){

                                    var departureAirport;
                                    var arrivalAirport;

                                    var dropdownDepartures = $('#dropdownDepartures')
                                    var dropdownArrivals = $('#dropdownArrivals')
                                    
                                    $('#departures').on('click', function(event){
                                        
                                        $.get('FlightsServlet', function(data){
                                            if(data.status == 'success'){
                                                dropdownDepartures.empty();
                                                for(i = 0; i < data.airport.length; i++) {
                                                    dropdownDepartures.append('<li><a class = dropdown-item href="#">' + data.airport[i].airportName + '</a></li>')
                                                }
                                            }
                                        })
                                    })
                                    
                                    $('#arrivals').on('click', function(event){
                                        
                                        $.get('FlightsServlet', function(data){
                                            if(data.status == 'success'){
                                                dropdownArrivals.empty();
                                                for(i = 0; i < data.airport.length; i++) {
                                                    dropdownArrivals.append('<li><a class = dropdown-item href="#">' + data.airport[i].airportName + '</a></li>')
                                                }
                                            }
                                        })
                                    })

                                    $('#dropdownDepartures').click(function(event){
                                        departureAirportt = $(event.target).text()
                                        console.log(departureAirportt)
                                        if(departureAirportt == null) {
                                            console.log("whaat")
                                        } else {
                                            departureAirport = departureAirportt
                                            
                                        }
                                        event.preventDefault()
                                        return false;

                                })

                                
                                $('#dropdownArrivals').click(function(event){
                                    var arrivalAirportt = $(event.target).text()
                                    if(arrivalAirportt == null){
                                        console.log("whaaaat")
                                    }else {
                                        arrivalAirport = arrivalAirportt
                                        console.log(arrivalAirportt)
                                    
                                    }
                                    event.preventDefault()
                                    return false;

                                })
                                var flight = data.flight
                                console.log("FLIIIGHT DATE ", data.flightDate)
                        
                                    console.log('whaaaaaaaaaaaaat?')
                                    
                                    var seatNumInput = $('#seatNumInput')
                                    var ticketPriceInput = $('#ticketPriceInput')
                                    var arrivalDateInput = $('#arrivalDateInput')
                                    var arrivalTimeInput = $('#arrivalTimeInput')
                                    
                                    
                                    $('#flightNumInput').text(flight.flightNum);
                                    var date = new Date(parseInt(flight.departureTime))
                                    var result = date.format('ddd mmm dd yyyy HH:MM:ss')
                                    $('#departureDateInput').text(result)		
                                    seatNumInput.val(flight.seatNum)
                                    ticketPriceInput.text(flight.ticketPrice)
                                    arrivalDateInput.val(data.flightDate)
                                   
                                    
                                    $('#updateSubmit').on('click', function(event){
                                        var conf = confirm("Are you sure that you want to change flight?")
                                        if(conf == false){
                                            return false;
                                        }else {
                                        
                                        var arrivalDate = arrivalDateInput.val()
                                        var arrivalTime = arrivalTimeInput.val()

                                        var seatNum = seatNumInput.val()
                                        var ticketPrice = ticketPriceInput.val()

                                        if(arrivalDate == ""){
                                            console.log('EMPTY')
                                            alert("Date can't be empty")
                                            event.preventDefault();
                                            return false;
                                            
                                        }
                
                                        if(arrivalTime == ""){
                                            console.log('EMPTY')
                                            alert("Date can't be empty")
                                            event.preventDefault();
                                            return false;
                                            
                                        }
                
                                        if(seatNum == ""){
                                            console.log('EMPTY')
                                            alert("Seat can't be empty")
                                            event.preventDefault();
                                            return false;
                                            
                                        }
                                        if(!parseInt(seatNum)){
                                            alert("Seat must be number")
                                            event.preventDefault();
                                            return false;
                                        }
                                        
                                        if(parseInt(seatNum) < 50){
                                            alert("Number of seats must be greater than 50")
                                            event.preventDefault();
                                            return false;
                                        }
                
                
                                        if(ticketPrice == ""){
                                            console.log('EMPTY')
                                            alert("Price can't be empty")
                                            event.preventDefault();
                                            return false;
                                            
                                        }
                                        
                                        if(!parseInt(ticketPrice)){
                                            alert("Ticket Price must be number")
                                            event.preventDefault();
                                            return false;
                                        }
                                         if(parseInt(ticketPrice) <= 0){
                                            alert("Price must be greater than 0")
                                            event.preventDefault();
                                            return false;
                                        }
                
                                        if(arrivalAirport == departureAirport){
                                            alert("Arrival airport can't be same as departure airport")
                                            event.preventDefault()
                                            return false
                                        }

                                        if(Date.parse(arrivalDate) < Date.parse(date)){
                                            alert('Arrival date must be after departure date');
                                            
                                            event.preventDefault();
                                            return false;
                                        }	
                                        
                                        params = {
                                            'action' : 'edit',
                                            'id' : id,
                                            'arrivalDate' : arrivalDate, 
                                            'arrivalTime' : arrivalTime,
                                            'departureAirport' : departureAirport,
                                            'arrivalAirport' : arrivalAirport,
                                            'seatNum' : seatNum,
                                            'ticketPrice' : ticketPrice
                                        }
                                        console.log(params)
                                        
                                        $.post('FlightsViewServlet', params, function(data){
                                            

                                            if (data.status == "failure") {
                                                alert(data.message)
                                                return
                                            }

                                            if (data.status == 'unauthenticated') {
                                                window.location.replace('login.html');
                                                return;
                                            }

                                            
                                            
                                            if(data.status == 'success'){
                                                console.log("you shall not pass")
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
                    }else {
                        window.location.replace('AdminWindow.html')
                    }
                })
			}else if(data.loggedInUserRole == 'USER'){
                window.location.replace('AdminWindow.html')
            }
		})
		
	}
	
	getFlight()
})