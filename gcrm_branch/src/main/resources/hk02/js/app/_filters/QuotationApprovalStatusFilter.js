define(["app"],function(A){A.registerFilter("QuotationApprovalStatusFilter",function(){var A={SAVING:"QUOTATION_APPROVAL_STATUS_SAVING",APPROVING:"QUOTATION_APPROVAL_STATUS_APPROVING",APPROVED:"QUOTATION_APPROVAL_STATUS_APPROVED",CANCEL:"QUOTATION_APPROVAL_STATUS_CANCEL"};return function(O){return A[O]?A[O]:O}})});