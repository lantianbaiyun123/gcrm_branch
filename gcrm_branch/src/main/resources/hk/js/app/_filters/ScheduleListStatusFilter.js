define(["app"],function(e){e.registerFilter("ScheduleListStatusFilter",function(){var e={reserved:"SCHEDULE_RESERVED",confirmed:"SCHEDULE_CONFIRMED",locked:"SCHEDULE_LOCKED",released:"SCHEDULE_RELEASED","schedule.schedulestatus.confirmed":"SCHEDULE_CONFIRMED","schedule.schedulestatus.locked":"SCHEDULE_LOCKED","schedule.schedulestatus.reserved":"SCHEDULE_RESERVED","schedule.schedulestatus.released":"SCHEDULE_RELEASED"};return function(E){return E in e?e[E]:"--"}})});