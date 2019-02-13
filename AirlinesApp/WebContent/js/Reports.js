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
    
    var reportsTable = $('#reportsTable')
    var minDateInput = $('#minDateInput')
    var maxDateInput = $('#maxDateInput')
    var minTimeInput = $('#minTimeInput')
    var maxTimeInput = $('#maxTimeInput')


   

    function getReports(){
        $.get('UserServlet', {'action': 'loggedInUserRole'}, function(data){
            console.log("AAADMIN")

            if (data.status == 'unauthenticated') {
				window.location.replace('login.html');
				return;
			}

			if(data.loggedInUserRole == 'ADMIN') {

                console.log("AAADMIN")
				$.get('UserServlet', {'action' : 'loggedInUserStatus'}, function(data) {
                    
                    if (data.status == 'unauthenticated') {
                        window.location.replace('login.html');
                        return;
                    }

					if(data.loggedInUserStatus == true) {
                        window.location.replace('AdminWindow.html')
                        return
                    }

                    var minDate = minDateInput.val()
                    var minTime = document.getElementById('minTimeInput').value
                    console.log("minTime", minTime )
                    var maxDate = maxDateInput.val()
                    var maxTime = maxTimeInput.val()

                    params = {
                        'minDate' : minDate,
                        'maxDate' : maxDate,  
                        'minTime' : minTime,   
                        'maxTime' : maxTime
                    }

                    console.log(params)

                    $.get('ReportsServlet', params,  function(data){
                        console.log("WHAAAAAAAAAAAAAAAT")

                        if (data.status == 'unauthenticated') {
                            window.location.replace('login.html');
                            return;
                        }

                        if(data.status == 'success'){
                            reportsTable.find('tr:gt(0)').remove();
                          
                            var reports = data.report
                            
                            
                       
                            var allFlights = data.allFlights;
                           
                            var countFlights;
                            for(var i in allFlights){
                                countFlights = allFlights[4]
                                
                            }
                            var allTickets = data.allTickets;

                            var countTickets;
                            for(var i in allTickets){
                                countTickets = allTickets[4]
                            }
                            var sumPricee = data.sumPrice;

                            var sum;
                            for(var i in sumPricee){
                                sum = sumPricee[4]
                            }

                            for(var r in reports) {
                                var name = reports[r].airportName
                                console.log(name)
                                if(name == null) {
                                    name = "" 
                                }else {
                                    name = reports[r].airportName
                                }
                                reportsTable.append(
                                    '<tr>' +
                                        '<td>' + name + '</td>' +
                                        '<td>' + reports[r].countFlights + '</td>' +
                                        '<td>' + reports[r].countTickets+ '</td>' +
                                        '<td>' + reports[r].sumPrice + '</td>' +
                                    '</tr>' 
                                    
                                )
                               
                            }
                            reportsTable.append(
                                '<tr>' +
                                    '<td></td>' +
                                    '<td>' + countFlights + '</td>' +
                                    '<td>' + countTickets + '</td>' +
                                    '<td>' + sum + '</td>' +
                                '</tr>'
                            )
                        }
                    })
                })
            }
        })
    }

    minDateInput.on('keyup', function(event){
        getReports()
        event.preventDefault()
        return false;
    })

    minTimeInput.on('keyup', function(event){
        getReports()
        event.preventDefault()
        return false;
    })

    maxDateInput.on('keyup', function(event){
        getReports()
        event.preventDefault()
        return false;
    })

    maxTimeInput.on('keyup', function(event){
        getReports()
        event.preventDefault()
        return false;
    })

    getReports()
})