define(["app"],function(S){S.registerFilter("AdSolutionStatusFilter",function(){var S={saving:"AD_SOLUTION_STATUS_saving",refused:"AD_SOLUTION_STATUS_refused",approving:"AD_SOLUTION_STATUS_approving",approved:"AD_SOLUTION_STATUS_approved",unconfirmed:"AD_SOLUTION_STATUS_unconfirmed",confirmed:"AD_SOLUTION_STATUS_confirmed",cancel:"AD_SOLUTION_STATUS_cancel"};return function(n){return n&&S[n]?S[n]:"--"}})});