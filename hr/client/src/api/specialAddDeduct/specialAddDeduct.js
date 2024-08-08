import request from "@/utils/request";

export const getSpecialAddDeductById = id =>
  request({
    url: "/specialAddDeduct/specialAddDeduct/" + id,
    method: "get"
  });

export const listSpecialAddDeductPage = search =>
  request({
    url: "/specialAddDeduct/list",
    method: "post",
    data: search
  });
export const downloadTemplate = search =>
  request({
    url: "/specialAddDeduct/downloadTemplate",
    method: "get",
    type: "blob",
    responseType: "arraybuffer"
  });
export const fileUpload = (form, taxYearMonth) =>
  request({
    url: `/specialAddDeduct/file/${taxYearMonth}`,
    method: "post",
    data: form
  });
export const listSpecialAddDeductAll = search =>
  request({
    url: "/specialAddDeduct/specialAddDeduct/listAll",
    method: "post",
    data: search
  });

export const saveSpecialAddDeduct = specialAddDeduct =>
  request({
    url: "/specialAddDeduct/specialAddDeduct/save",
    method: "post",
    data: specialAddDeduct
  });

export const deleteSpecialAddDeduct = specialAddDeduct =>
  request({
    url: "/specialAddDeduct/specialAddDeduct/delete",
    method: "post",
    data: specialAddDeduct
  });

export const bulkInsertSpecialAddDeduct = specialAddDeducts =>
  request({
    url: "/specialAddDeduct/specialAddDeduct/bulkInsert",
    method: "post",
    data: specialAddDeducts
  });

export const bulkUpdateSpecialAddDeduct = specialAddDeducts =>
  request({
    url: "/specialAddDeduct/specialAddDeduct/bulkUpdate",
    method: "post",
    data: specialAddDeducts
  });

export const bulkDeleteSpecialAddDeduct = specialAddDeducts =>
  request({
    url: "/specialAddDeduct/specialAddDeduct/bulkDelete",
    method: "post",
    data: specialAddDeducts
  });
export const getTaxDeclareConfig = () =>
  request({
    url: "/specialAddDeduct/getTaxDeclareConfig",
    method: "get"
  });
export const saveTaxDeclareConfig = search =>
  request({
    url: "/specialAddDeduct/saveTaxDeclareConfig",
    method: "post",
    data: search
  });
