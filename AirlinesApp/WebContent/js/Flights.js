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

	
	var flightNumFilterInput = $('#flightNumFilterInput');
	var departureTimeInput = $('#departureTimeInput');
	var departureDateInput = $('#departureDateInput');
	var arrivalTimeInput = $('#arrivalTimeInput');
	var arrivalDateInput = $('#arrivalDateInput');
	var minTicketPriceFilterInput = $('#minTicketPriceFilterInput');
	var maxTicketPriceFilterInput = $('#maxTicketPriceFilterInput');
	var departureAirport;
	var arrivalAirport;
	var flightsTable = $('#flightsTable');
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
		var departureAirportt = $(event.target).text()
		if(departureAirportt != null) {
			departureAirport = departureAirportt
			getFlights()

			event.preventDefault()
			return false;
		}
		

	})

	
	$('#dropdownArrivals').click(function(event){
		var arrivalAirportt = $(event.target).text()
		if(arrivalAirportt != null){
			arrivalAirport = arrivalAirportt
			getFlights()
	 
			event.preventDefault()
			return false;
		}
	  

   })
	
	function getFlights(){
		
	
		var flightNumFilter = flightNumFilterInput.val();
		var minTicketPriceFilter = minTicketPriceFilterInput.val();
		var maxTicketPriceFilter = maxTicketPriceFilterInput.val();
		var departureTimeFilter = departureTimeInput.val();
		var departureDateFilter = departureDateInput.val();
		var arrivalTimeFilter = arrivalTimeInput.val();
		var arrivalDateFilter = arrivalDateInput.val();
		
		params = {
			'flightNumFilter' : flightNumFilter,
			'minTicketPriceFilter' : minTicketPriceFilter,
			'maxTicketPriceFilter' : maxTicketPriceFilter,
			'departureTimeFilter' : departureTimeFilter,
			'departureDateFilter' : departureDateFilter,
			'arrivalTimeFilter' : arrivalTimeFilter,
			'arrivalDateFilter' : arrivalDateFilter,
			'departureAirport' : departureAirport,
			'arrivalAirport' : arrivalAirport

		}

		console.log(params + "params" )
		
		$.get('FlightsServlet', params, function(data){
			console.log(data);

			if(data.status == 'unauthenticated'){
				return;
			}
				
			if(data.status == 'success'){
				flightsTable.find('tr:gt(0)').remove();

			

				
				filteredFlight = data.filteredFlight
				
		
			
				for(var it in filteredFlight) {
					var departureDate = new Date(filteredFlight[it].departureTime)
					var arrivalDate = new Date(filteredFlight[it].arrivalTime)
					flightsTable.append(
						'<tr>' +
							'<td><a href="ViewFlight.html?id=' + filteredFlight[it].id + '">' + filteredFlight[it].flightNum + '</td>' +
							'<td>' + departureDate.toLocaleString() + '</td>' +
							'<td>' + arrivalDate.toLocaleString() + '</td>' +
							'<td>' + filteredFlight[it].departures.airportName + '</td>' +
							'<td>' + filteredFlight[it].arrivals.airportName + '</td>' +
							'<td>' + filteredFlight[it].ticketPrice + '</td>' +
						'</tr>'
					)

	
					
				}

				

				
			

				

			}


			
			
		
		})
	}
	

	
	
	flightNumFilterInput.on('keyup', function(event){
		getFlights()
		
		event.preventDefault();
		return false;
	})
	
	minTicketPriceFilterInput.on('keyup', function(event){
		getFlights()
		
		event.preventDefault();
		return false;
	})
	
	maxTicketPriceFilterInput.on('keyup', function(event){
		getFlights()
		
		event.preventDefault();
		return false;
	})
		
	departureTimeInput.on('keyup', function(event){
		getFlights()
		
		event.preventDefault();
		return false;
	})
	
		
	departureDateInput.on('keyup', function(event){
		getFlights()
		
		event.preventDefault();
		return false;
	})
	
	arrivalTimeInput.on('keyup', function(event){
		getFlights()
		
		event.preventDefault();
		return false;
	})
	
	arrivalDateInput.on('keyup', function(event){
		getFlights()
		
		event.preventDefault();
		return false;
	})
	

	
	
	
	getFlights()
	
})