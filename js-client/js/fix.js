/**
 * Created by antonsavitsky on 15.01.16.
 */
(function($) {
    $.fn.AddXbutton = function(options) {
        var defaults = {
            img: 'x.gif'
        };
        var opts = $.extend(defaults, options);
        $(this)
            .after($('<input type="image" id="xButton" src="' + opts['img'] + '" />')
                .css({ 'display': 'none', 'cursor': 'pointer', 'marginLeft': '-15px' })
                .click(function() {
                    $('#carName').val('').focus();
                    $('#xButton').hide();
                }))
            .keyup(function() { //на кейап мы проверяем, показывать нам крестик или нет
                if ($(this).val().length > 0) {
                    $('#xButton').show(); //если текст какой-нибудь есть, то показываем
                } else {
                    $('#xButton').hide();
                }
            });
        if ($(this).val() != '') $('#xButton').show(); //если при загрузке страницы в текстовом поле что-то есть, крестик надо сразу показать
    };
})(jQuery);

$(document).ready(function() {
    $('#carName').AddXbutton({ img: 'images/x.gif' });
});