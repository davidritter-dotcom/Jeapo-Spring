import { unsafeCSS, registerStyles } from '@vaadin/vaadin-themable-mixin/register-styles';

import vaadinGridCss from 'themes/custom-theme/components/vaadin-grid.css?inline';


if (!document['_vaadintheme_custom-theme_componentCss']) {
  registerStyles(
        'vaadin-grid',
        unsafeCSS(vaadinGridCss.toString())
      );
      
  document['_vaadintheme_custom-theme_componentCss'] = true;
}

if (import.meta.hot) {
  import.meta.hot.accept((module) => {
    window.location.reload();
  });
}

