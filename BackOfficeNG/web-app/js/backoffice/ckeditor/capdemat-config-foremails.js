// Doc: http://nightly-v4.ckeditor.com/ckeditor_api/#!/api/CKEDITOR.config
CKEDITOR.editorConfig = function(config) {
  config.dataIndentationChars = '  '
  config.defaultLanguage = 'fr'
  config.disableNativeSpellChecker = false
  config.enterMode = CKEDITOR.ENTER_BR
  config.format_tags = 'h1;h2;p;div;pre'
  config.height = '400px'
  config.linkShowAdvancedTab = false
  config.linkShowTargetTab = false
  config.resize_enabled = false

  // Config specific to the email editor

  config.extraPlugins = 'variables'
  config.fullPage = true

  config.toolbar = 'foremails'
  config.toolbar_foremails =
    [ { name: 'Format', items: [ 'Format'  ] }
    , { name: 'Paragraphe', items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote' ] }
    , { name: 'Style', items: [ 'Bold', 'Italic', 'Underline', 'Strike', '-', 'TextColor', 'BGColor', '-', 'RemoveFormat' ] }
    , { name: 'Lien', items: [ 'Link', 'Unlink' ] }
    , { name: 'Variables', items: [ 'Variables' ] }
    , { name: 'Historique', items: [ 'Undo', 'Redo', '-', 'ShowBlocks' ] }
    ]
}
