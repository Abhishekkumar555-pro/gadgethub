<!DOCTYPE html> 
<html lang="en"> 
  <head> 
    <meta charset="utf-8" /> 
    <meta name="viewport" content="width=device-width, initial-scale=1" /> 
    <title>Register</title> 
    <link 
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" 
      rel="stylesheet" 
      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" 
      crossorigin="anonymous" 
    /> 
    <link rel="stylesheet" href="style2.css"> 
  </head> 
  <body> 
                    <jsp:include page="header.jsp"/>
  <%
            String message = request.getParameter("message");
        %>

    <div class="container mt-5 mb-5"> 
      <div class="row justify-content-center"> 
        <form action="./RegisterServlet" class="col-md-6 myform" method="post"> 
          <div class="text-center mt-3"> 
            <h2 class="text-primary">Registration Form</h2> 
            <%  if (message != null) {  %>
                        <p  style="color: crimson" > <%=message %></p>
                        <% } %>
          </div> 
 
          <div class="row"> 
            <div class="col-md-6 form-group"> 
              <label for="user_name">Name</label> 
              <input type="text" name="username" class="form-control" id="user_name" /> 
            </div> 
 
            <div class="col-md-6 form-group"> 
              <label for="user_email">Email</label> 
              <input type="text" name="useremail" class="form-control" id="user_email" /> 
            </div> 
          </div> 
 
          <div class="form-group"> 
            <label for="address">Address </label> 
            <textarea name="useraddress" id="address" class="form-control"></textarea> 
          </div> 
 
          <div class="row"> 
            <div class="col-md-6 form-group"> 
              <label for="mobile">Mobile No.</label> 
              <input type="number" name="usermobile" class="form-control" id="mobile" /> 
            </div> 
 
            <div class="col-md-6 form-group"> 
              <label for="pincode">Pincode</label> 
              <input type="number" name="userpincode" class="form-control" id="pincode" /> 
            </div> 
          </div> 
 
          <div class="row"> 
            <div class="col-md-6 form-group"> 
              <label for="password">Password</label> 
              <input type="password" name="password" class="form-control" id="password" /> 
            </div> 
 
            <div class="col-md-6 form-group"> 
              <label for="cpassword">Confirm Password</label> 
              <input type="password" name="cnfpassword" class="form-control" id="cpassword" /> 
            </div> 
          </div> 
 
          <div class="row mt-3 mb-3 text-center"> 
 
            <div class="col-md-6 "> 
              <button type="reset" class="btn btn-danger">Reset</button> 
            </div> 
 
            <div class="col-md-6"> 
              <button type="submit" class="btn btn-primary">Register</button> 
            </div> 
          </div> 
 
        </form> 
      </div> 
    </div> 
               <jsp:include page="footer.jsp"/>

    <script 
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
      integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" 
      crossorigin="anonymous" 
    ></script> 
  </body> 
</html>