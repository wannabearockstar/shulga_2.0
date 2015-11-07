var app = null;

(function ($) {

    'use strict';

    $(function () {
        var parts = window.location.pathname.split('/');
        var id = parts[2];

        var auditory_times = $('#auditory_times');
        var discipline_times = $('#discipline_times');
        var discipline_auditories = $('#discipline_auditories');

        var options = {
            session_id: id,
            controls: {
                auditory_times: auditory_times,
                discipline_times: discipline_times,
                discipline_auditories: discipline_auditories
            }
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
                app.newDisciplineTimesBound(discipline_times);
                app.newDisciplineAuditoriesBound(discipline_auditories);
            });
    });

    function App(options) {
        var self = this;

        self.controls = options.controls;
        self.session_id = options.session_id;

        self.collect = function () {
            var res = {
                'auditory_times': {},
                'discipline_times': {},
                'discipline_auditories': {}
            };

            // collect auditory times boundaries
            self.controls.auditory_times.find('.bound-model').each(function () {
                var $this = $(this);

                var auditory_alias = $this.find('.auditory').val();
                var auditory = utils.entity.bind('auditory', 'alias', auditory_alias);

                var week_days = _.map($this.find('.day'), function (item) {
                    var week_day_id = $(item).val();
                    var week_day = utils.entity.bind('week_day', 'id', week_day_id);
                    return week_day.id;
                });

                var times = _.map($this.find('.time'), function (item) {
                    var time_id = $(item).val();
                    var time = utils.entity.bind('time', 'id', time_id);
                    return time.id;
                });

                res['auditory_times'][auditory.id] = {
                    'days': week_days,
                    'times': times
                };
            });

            // collect discipline times boundaries
            self.controls.discipline_times.find('.bound-model').each(function () {
                var $this = $(this);

                var discipline_alias = $this.find('.discipline').val();
                var discipline = utils.entity.bind('discipline', 'alias', discipline_alias);

                var week_days = _.map($this.find('.day'), function (item) {
                    var week_day_id = $(item).val();
                    var week_day = utils.entity.bind('week_day', 'id', week_day_id);
                    return week_day.id;
                });

                var times = _.map($this.find('.time'), function (item) {
                    var time_id = $(item).val();
                    var time = utils.entity.bind('time', 'id', time_id);
                    return time.id;
                });

                res['discipline_times'][discipline.id] = {
                    'days': week_days,
                    'times': times
                };
            });

            // collect discipline auditories boundaries
            self.controls.discipline_auditories.find('.bound-model').each(function () {
                var $this = $(this);

                var discipline_alias = $this.find('.discipline').val();
                var discipline = utils.entity.bind('discipline', 'alias', discipline_alias);

                var auditories =_.map($this.find('.auditory'), function (item) {
                    var auditory_alias = $(item).val();
                    var auditory = utils.entity.bind('auditory', 'alias', auditory_alias);
                    return auditory.id;
                });

                res['discipline_times'][discipline.id] = {
                    'values': auditories
                };
            });

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
            window.location = '/input/' + self.session_id + '/models';
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


