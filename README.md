# Airlines-App
Web application for booking and purchasing airline tickets, made using Java Servlets, AJAX, JQuery, SQL, JSON, HTML5, CSS3 and Bootrstrap. Application can be used by three types of users: admins, unregistered users and registered users.

Features
- Unsigned user can login to the system.
- Unregistered users can register to the system.
- Users can logout from system.
- Main page contains list of flights with functionalities of searching and sorting flights. Users can also view a single flight. Registered users can reserve or purchase ticket by clicking on flight. Admins can add new or delete existing flights.
- Administrators have a table that contains list of booked flights and table that contains list of purchased flights.
- Ticket booking/purchase is done in three phases. In first phase user selects departure flight among available flights. Second phase is optional and it includes selecting a return flight among available flights. Third phase includes seat selection in departure flight and/or seat selection at return flight. It also includes filling up passengers name and last name and showing total flight price. User can always go back to previous phase.
- User can change or delete only his reservation.
- Admin can make a purchase of reserved ticket.
- Admins can see all users, they can change their data, block them or delete them. Users can only see and change their profiles.
- Users can view their purchased/booked tickets.
- Admins can see a report of flights and sold tickets per each airport and total price of purchased tickets.
