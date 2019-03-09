(function ($) {

  $(document).ready(function () {
    $('#signin').validate({
      rules:{
        user_email:{required:true,email:true,remote:{type: "post", url:"/login"}},
        user_password:{required:true,minlength:8,maxlength:16},
        repeat_user_password:{equalTo:"#user_password"}
      },
      messages:{
        user_email:{
          minlength:message
        }
      }
    })
  })
})