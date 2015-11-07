var app = null;

(function ($) {

    'use strict';

    $(function () {
        var parts = window.location.pathname.split('/');
        var id = parts[2];

        var options = {
            session_id: id
        };

        $.when(
            utils.template.init("bound_model"),
            utils.entity.initFromController('config', '/input/' + id + '/config')
        ).then(function () {

            var groups = utils.entity.list("groups");
            var auditories = utils.entity.list("auditories");
            var professors = utils.entity.list("professors");
            var lesson_types = utils.entity.list("lesson_types");
            var disciplines = utils.entity.list("disciplines");

            utils.entity.initFromExisting("group", groups);
            utils.entity.initFromExisting("auditory", auditories);
            utils.entity.initFromExisting("professor", professors);
            utils.entity.initFromExisting("lesson_type", lesson_types);
            utils.entity.initFromExisting("discipline", disciplines);

        }).then(function () {

                app = new App(options);

                $('#prev-step').on('click', function () {
                    app.prevStep();
                });

                $('#next-step').on('click', function () {
                    app.nextStep();
                });

                $('.bound-info').on('click', '.new-model', function () {

                    var container = $(this).parents('.bound-info');
                    app.newModel(container);

                }).each(function () {

                    var $this = $(this);
                    app.newModel($this);

                });
            });
    });

    function App(options) {
        var self = this;

        self.session_id = options.session_id;

        self.collect = function () {
            // todo: collect form into models
            return groups;
        };

        self.newModel = function (contaner) {
            var last_elem = contaner.find('.new-model-container');
            var view = utils.template.render("model", new GroupCurriculum());
            last_elem.before(view);
        };

        self.prevStep = function () {
            window.location = '/input/' + self.session_id + '/steps/models';
        };

        self.nextStep = function () {
            $.ajax({
                url: '/input/' + session_id + '/boundaries',
                method: 'PUT',
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: JSON.stringify(self.collect()),
                async: true
            }).then(function () {
                return $.ajax({
                    url: '/input/' + session_id + '/run',
                    method: 'POST',
                    async: true
                });
            }).then(function (resp) {
                window.location = '/status/' + resp.data;
            });
        };
    }

})(jQuery);


