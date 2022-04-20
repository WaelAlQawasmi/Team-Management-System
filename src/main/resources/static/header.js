
      window.onload = myFunction;
      function myFunction() {

                   var username = localStorage.getItem("username");
                    document.getElementById('Profile').innerHTML = username;

                      var numberOfNotification = localStorage.getItem("numberOfNotification");
                      if (numberOfNotification === "0"){
                          document.getElementById("numberOfNotificatinos").visibility = "hidden";
                      }else {
                          document.getElementById('numberOfNotificatinos').innerHTML = numberOfNotification;
                      }

                      }

