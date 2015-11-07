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
            utils.template.init("auditory_times_bound"),
            utils.template.init("discipline_times_bound"),
            utils.template.init("discipline_auditories_bound"),
            utils.entity.initFromController('config', '/input/' + id + '/config')
        ).then(function () {

            var groups = utils.entity.list("config")["groups"];
            var auditories = utils.entity.list("config")["auditories"];
            var professors = utils.entity.list("config")["professors"];
            var lesson_types = utils.entity.list("config")["lesson_types"];
            var disciplines = utils.entity.list("config")["disciplines"];
            var times = utils.entity.list("config")["times"];
            var week_days = utils.entity.list("config")["week_days"];
            week_days = _.map(week_days, WeekDay.fromId);

            utils.entity.initFromExisting("group", groups);
            utils.entity.initFromExisting("auditory", auditories);
            utils.entity.initFromExisting("professor", professors);
            utils.entity.initFromExisting("lesson_type", lesson_types);
            utils.entity.initFromExisting("discipline", disciplines);
            utils.entity.initFromExisting("time", times);
            utils.entity.initFromExisting("week_day", week_days);

        }).then(function () {

                var auditory_times = $('#auditory_times');
                var discipline_times = $('#discipline_times');
                var discipline_auditories = $('#discipline_auditories');

                app = new App(options);

                $('#prev-step').on('click', function () {
                    app.prevStep();
                });

                $('#next-step').on('click', function () {
                    app.nextStep();
                });

                $('.bound-info').on('click', '.bound-remove', function () {
                    var container = $(this).parents('.bound-model');
                    app.removeModel(container);
                });

                auditory_times.on('click', '.new-model', function () {
                    app.newAuditoryTimesBound(auditory_times);
                });

                discipline_times.on('click', '.new-model', function () {
                    app.newDisciplineTimesBound(discipline_times);
                });

                discipline_auditories.on('click', '.new-model', function () {
                    app.newDisciplineAuditoriesBound(discipline_auditories);
                });

                app.newAuditoryTimesBound(auditory_times);
                //app.newDisciplineTimesBound(discipline_times);
                //app.newDisciplineAuditoriesBound(discipline_auditories);
            });
    });

    function App(options) {
        var self = this;

        self.session_id = options.session_id;

        self.collect = function () {
            var res = {
                'auditory_times': {},
                'discipline_times': {},
                'discipline_auditories': {}
            };

            //$('.group-info').each(function () {
            //    var $this = $(this);
            //    var group_alias = $this.find('.group').val();
            //    var group = utils.entity.bind('group', 'alias', group_alias);
            //
            //    // todo: add validation
            //
            //    var base_unit = {
            //        'group_id': group.id,
            //        'discipline_id': 0,
            //        'professor_id': 0,
            //        'lesson_type_id': 0
            //    };
            //
            //    $this.find('.group-model').each(function () {
            //        var $this = $(this);
            //
            //        // todo: add binding by fio
            //        var professor_last_name = $this.find('.professor').val();
            //        var professor = utils.entity.bind('professor', 'last_name', professor_last_name);
            //
            //        var discipline_alias = $this.find('.discipline').val();
            //        var discipline = utils.entity.bind('discipline', 'alias', discipline_alias);
            //
            //        var lesson_type_id = $this.find('.lesson-type').val();
            //        var lesson_type = utils.entity.bind('lesson_type', 'id', lesson_type_id);
            //
            //        var unit = $.extend({}, base_unit, {
            //            professor_id: professor.id,
            //            discipline_id: discipline.id,
            //            lesson_type_id: lesson_type.id
            //        });
            //
            //        res.push(unit);
            //    });
            //});

            return res;
        };

        self.newAuditoryTimesBound = function (contaner) {
            var last_elem = contaner.find('.new-model-container');
            var view = utils.template.render("auditory_times_bound", new AuditoryTimesBound());
            last_elem.before(view);
        };

        self.newDisciplineTimesBound = function (contaner) {
            var last_elem = contaner.find('.new-model-container');
            var view = utils.template.render("discipline_times_bound", new DisciplineTimesBound());
            last_elem.before(view);
        };

        self.newDisciplineAuditoriesBound = function (contaner) {
            var last_elem = contaner.find('.new-model-container');
            var view = utils.template.render("discipline_auditories_bound", new DisciplineAuditoriesBound());
            last_elem.before(view);
        };

        self.prevStep = function () {
            window.location = '/input/' + self.session_id + '/steps/models';
        };

        self.nextStep = function () {
            $.ajax({
                url: '/input/' + self.session_id + '/boundaries',
                method: 'PUT',
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: JSON.stringify(self.collect()),
                async: true
            }).then(function () {
                return $.ajax({
                    url: '/input/' + self.session_id + '/run',
                    method: 'POST',
                    async: true
                });
            }).then(function (resp) {
                window.location = '/status/' + resp.data;
            });
        };

        self.removeModel = function (container) {
            container.remove();
        };
    }

})(jQuery);


