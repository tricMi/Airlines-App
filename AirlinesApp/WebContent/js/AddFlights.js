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
	
	var flightNumInput = $('#flightNumInput');
	var seatNumInput = $('#seatNumInput')
	var ticketPriceInput = $('#ticketPriceInput')
	var departureDateInput = $('#departureDateInput')
	var departureTimeInput = $('#departureTimeInput')
	var arrivalDateInput = $('#arrivalDateInput')
	var arrivalTimeInput = $('#arrivalTimeInput')
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
			
			
	
			$('#flightSubmit').on('click', function(event){
				var conf = confirm("Are you sure that you want to add flight?")
				if(conf == false){
					return false
				}else {
			
						var flightNum = flightNumInput.val()
						var departureDate = departureDateInput.val()
						var departureTime = departureTimeInput.val()
						var arrivalDate = arrivalDateInput.val()
						var arrivalTime =  arrivalTimeInput.val()
						var seatNum = seatNumInput.val()
						var ticketPrice = ticketPriceInput.val()

						
						if(Date.parse(arrivalDate) < Date.parse(departureDate)){
							alert('Arrival date must be after departure date');
							
							event.preventDefault();
							return false;
						}	

						if(flightNum == ""){
							console.log('EMPTY')
							alert("Name can't be empty")
							event.preventDefault();
							return false;
							
						}

						if(departureDate == ""){
							console.log('EMPTY')
							alert("Date can't be empty")
							event.preventDefault();
							return false;
							
						}

						if(departureTime == ""){
							console.log('EMPTY')
							alert("Time can't be empty")
							event.preventDefault();
							return false;
							
						}

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

						params = {
							'action' : 'add',
							'flightNum' : flightNum,
							'departureDate' : departureDate,
							'departureTime' : departureTime,
							'arrivalDate' : arrivalDate,
							'arrivalTime' : arrivalTime,
							'departureAirport' : departureAirport,
							'arrivalAirport' : arrivalAirport,
							'seatNum' : seatNum,
							'ticketPrice' : ticketPrice
						}
						
						$.post('FlightsViewServlet', params, function(data){
							

							if (data.status == 'unauthenticated') {
								window.location.replace('login.html');
								return;
							}

							if(data.status == "failure"){
								alert(data.message)
							}

							if (data.status == 'success') {
								alert('Flight added successfully')
								window.location.replace('AdminWindow.html');
							}
							
						})
						
						event.preventDefault();
						return false;
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
})