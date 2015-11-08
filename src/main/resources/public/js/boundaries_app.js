var app = null;

(function ($) {

    'use strict';

    $(function () {
        var auditory_times = $('#auditory_times');
        var discipline_times = $('#discipline_times');
        var discipline_auditories = $('#discipline_auditories');
        var config = JSON.parse($('#config').text() || '{}');

        var options = {
            config: config,
            session_id: window.location.pathname.split('/')[2],
            controls: {
                auditory_times: auditory_times,
                discipline_times: discipline_times,
                discipline_auditories: discipline_auditories
            }
        };

        $.when(
            utils.template.init("day_bound"),
            utils.template.init("time_bound"),
            utils.template.init("auditory_bound"),
            utils.template.init("auditory_times_bound"),
            utils.template.init("discipline_times_bound"),
            utils.template.init("discipline_auditories_bound")
        ).then(function () {

            utils.entity.initFromExisting('config', config);

            var groups = config["groups"];
            var auditories = config["auditories"];
            var professors = config["professors"];
            var lesson_types = config["lesson_types"];
            var disciplines = config["disciplines"];
            var times = config["times"];
            var week_days = config["week_days"];
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
            }).on('click', '.new-day-bound', function () {
                app.newWeekDayBound($(this));
            }).on('click', '.new-time-bound', function () {
                app.newTimeBound($(this));
            }).on('click', '.new-auditory-bound', function () {
                app.newAuditoryBound($(this));
            });

            auditory_times.on('click', '.new-model', function () {
                app.newAuditoryTimesBound();
            });

            discipline_times.on('click', '.new-model', function () {
                app.newDisciplineTimesBound();
            });

            discipline_auditories.on('click', '.new-model', function () {
                app.newDisciplineAuditoriesBound();
            });

            var auditory_src = _.map(utils.entity.list('auditory'), function (item) {
                return item.alias;
            });

            auditory_src = _.map(_.groupBy(auditory_src,function(item){
                return item;
            }),function(grouped){
                return grouped[0];
            });

            var discipline_src = _.map(utils.entity.list('discipline'), function (item) {
                return item.alias;
            });

            discipline_src = _.map(_.groupBy(discipline_src,function(item){
                return item;
            }),function(grouped){
                return grouped[0];
            });

            $(document).on('keydown.autocomplete', '.auditory', function () {
                $(this).autocomplete({ source: auditory_src });
            }).on('keydown.autocomplete', '.discipline', function () {
                $(this).autocomplete({ source: discipline_src });
            });
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
                try {
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
                }
                catch (err) {
                    // nothing
                }
            });

            // collect discipline times boundaries
            self.controls.discipline_times.find('.bound-model').each(function () {
                try {
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
                }
                catch (err) {
                    // nothing
                }
            });

            // collect discipline auditories boundaries
            self.controls.discipline_auditories.find('.bound-model').each(function () {
                try {
                    var $this = $(this);

                    var discipline_alias = $this.find('.discipline').val();
                    var discipline = utils.entity.bind('discipline', 'alias', discipline_alias);

                    var auditories =_.map($this.find('.auditory'), function (item) {
                        var auditory_alias = $(item).val();
                        var auditory = utils.entity.bind('auditory', 'alias', auditory_alias);
                        return auditory.id;
                    });

                    res['discipline_auditories'][discipline.id] = {
                        'values': auditories
                    };
                }
                catch (err) {
                    // nothing
                }
            });

            return res;
        };

        self.newAuditoryTimesBound = function () {
            var last_elem = self.controls.auditory_times.find('.new-model-container');
            var view = utils.template.render("auditory_times_bound", new AuditoryTimesBound());
            last_elem.before(view);
        };

        self.newDisciplineTimesBound = function () {
            var last_elem = self.controls.discipline_times.find('.new-model-container');
            var view = utils.template.render("discipline_times_bound", new DisciplineTimesBound());
            last_elem.before(view);
        };

        self.newDisciplineAuditoriesBound = function () {
            var last_elem = self.controls.discipline_auditories.find('.new-model-container');
            var view = utils.template.render("discipline_auditories_bound", new DisciplineAuditoriesBound());
            last_elem.before(view);
        };

        self.newTimeBound = function (container) {
            var view = utils.template.render("time_bound", new DayTime());
            container.before(view);
        };

        self.newWeekDayBound = function (container) {
            var view = utils.template.render("day_bound", new WeekDay());
            container.before(view);
        };

        self.newAuditoryBound = function (container) {
            var view = utils.template.render("auditory_bound", new WeekDay());
            container.before(view);
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

        self.renderAuditoryTimesBounds = function (config) {
            var data = AuditoryTimesBound.fromSchedulerConfig(config);
            var last_elem = self.controls.auditory_times.find('.new-model-container');

            _.each(data, function (bound) {
                var view = utils.template.render("auditory_times_bound", bound);
                last_elem.before(view);
            });
        };

        self.renderDisciplineTimesBounds = function (config) {
            var data = DisciplineTimesBound.fromSchedulerConfig(config);
            var last_elem = self.controls.discipline_times.find('.new-model-container');

            _.each(data, function (bound) {
                var view = utils.template.render("discipline_times_bound", bound);
                last_elem.before(view);
            });
        };

        self.renderDisciplineAuditoriesBounds = function (config) {
            var data = DisciplineAuditoriesBound.fromSchedulerConfig(config);
            var last_elem = self.controls.discipline_auditories.find('.new-model-container');

            _.each(data, function (bound) {
                var view = utils.template.render("discipline_auditories_bound", bound);
                last_elem.before(view);
            });
        };

        self.renderAuditoryTimesBounds(options.config);
        self.renderDisciplineTimesBounds(options.config);
        self.renderDisciplineAuditoriesBounds(options.config);
    }

})(jQuery);


