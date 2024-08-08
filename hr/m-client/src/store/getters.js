const getters = {
  propInspectTask: state => state.property.propInspectTask,
  propInspectLine: state => state.property.propInspectLine,
  propInspectPoint: state => state.property.propInspectPoint,
  propInspectTaskSite: state => state.property.propInspectTaskSite,
  setPropertyScrollTop: state => state.property.setPropertyScrollTop,
  propInspectType:  state => state.property.propInspectType,

  facTask: state => state.fac.facTask,
  setFacScrollTop: state => state.fac.setFacScrollTop,
};

export default getters
