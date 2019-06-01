$(document).ready(function () {
    $('#password').keyup(function () {
        $('#mensaje').html(checkStrength($('#password').val()))
    })
    function checkStrength(password) {
        var strength = 0
        if (password.length < 12) {
            $('#mensaje').removeClass()
            $('#mensaje').addClass('Corta')
            return 'Demasiado corta'
        }
        if (password.length > 7) strength += 1
        
        if (password.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/)) strength += 1
        
        if (password.match(/([a-zA-Z])/) && password.match(/([0-9])/)) strength += 1
       
        if (password.match(/([!,%,&,@,#,$,^,*,?,_,~])/)) strength += 1
     
        if (password.match(/(.*[!,%,&,@,#,$,^,*,?,_,~].*[!,%,&,@,#,$,^,*,?,_,~])/)) strength += 1
       
        if (strength < 2) {
            $('#mensaje').removeClass()
            $('#mensaje').addClass('Debil')
            return 'Debil'
        } else if (strength == 2) {
            $('#mensaje').removeClass()
            $('#mensaje').addClass('Buena')
            return 'Buena'
        } else {
            $('#mensaje').removeClass()
            $('#mensaje').addClass('Fuerte')
            return 'Fuerte'
        }
    }
});
    