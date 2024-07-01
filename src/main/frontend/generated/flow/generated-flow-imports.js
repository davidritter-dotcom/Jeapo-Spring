import '@vaadin/polymer-legacy-adapter/style-modules.js';
import '@vaadin/vertical-layout/theme/lumo/vaadin-vertical-layout.js';
import '@vaadin/common-frontend/ConnectionIndicator.js';
import '@vaadin/vaadin-lumo-styles/color-global.js';
import '@vaadin/vaadin-lumo-styles/typography-global.js';
import '@vaadin/vaadin-lumo-styles/sizing.js';
import '@vaadin/vaadin-lumo-styles/spacing.js';
import '@vaadin/vaadin-lumo-styles/style.js';
import '@vaadin/vaadin-lumo-styles/vaadin-iconset.js';

const loadOnDemand = (key) => {
  const pending = [];
  if (key === '997ad25fcb548a17733e93d21e682300ea92d30611bf30dc1fd262e370ba0f17') {
    pending.push(import('./chunks/chunk-1c55840e32c1d3cb96681df18cb50e72d5cbe11933eb6ca5528ddbc95ba23d6d.js'));
  }
  if (key === 'b4a2ed9853df7d9593b11c08fc3e17823aa283ab2f88a4456b44900d0fdbf1e6') {
    pending.push(import('./chunks/chunk-1c55840e32c1d3cb96681df18cb50e72d5cbe11933eb6ca5528ddbc95ba23d6d.js'));
  }
  if (key === '589fb41f0f0faa1688fa425adee1779ce448356fd2e841c10ea4725556c7d4e2') {
    pending.push(import('./chunks/chunk-d8142f12c2c13018e8b34ec670058f12a2472a0d8f81348a69f484d7a061c136.js'));
  }
  if (key === '023133f2b76a55f5a008274a438b3fc7598e82778d16f7414276fb27e33c8712') {
    pending.push(import('./chunks/chunk-1c55840e32c1d3cb96681df18cb50e72d5cbe11933eb6ca5528ddbc95ba23d6d.js'));
  }
  return Promise.all(pending);
}

window.Vaadin = window.Vaadin || {};
window.Vaadin.Flow = window.Vaadin.Flow || {};
window.Vaadin.Flow.loadOnDemand = loadOnDemand;
window.Vaadin.Flow.resetFocus = () => {
 let ae=document.activeElement;
 while(ae&&ae.shadowRoot) ae = ae.shadowRoot.activeElement;
 return !ae || ae.blur() || ae.focus() || true;
}