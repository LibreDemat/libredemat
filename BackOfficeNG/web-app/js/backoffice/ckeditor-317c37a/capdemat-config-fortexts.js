// Doc: http://nightly-v4.ckeditor.com/ckeditor_api/#!/api/CKEDITOR.config
CKEDITOR.editorConfig = function(config) {
  config.customConfig = 'capdemat-config.js'
// TODO apply styles for h3 and h4 that look like h1 and h2
//
// See http://docs.cksource.com/CKEditor_3.x/Developers_Guide/Styles
  config.format_h1 = { element: 'h3', attributes: { 'class': 'contentTitle3' } }
  config.format_h2 = { element: 'h4', attributes: { 'class': 'contentTitle4' } }
}
