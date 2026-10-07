package com.trevornk.ramblr

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView

/** Russian labels for the settings UI that predates Android string resources. */
object RussianUi {
    private val labels = mapOf(
        "Checking..." to "Проверка…",
        "Cancel" to "Отмена",
        "Close" to "Закрыть",
        "Save" to "Сохранить",
        "Delete" to "Удалить",
        "Clear" to "Очистить",
        "Clear all" to "Очистить всё",
        "Continue" to "Продолжить",
        "Not now" to "Не сейчас",
        "Restore" to "Восстановить",
        "Settings" to "Настройки",
        "Status" to "Состояние",
        "Setup" to "Первоначальная настройка",
        "Transcription" to "Распознавание речи",
        "Cleanup" to "Корректировка текста",
        "Live Preview" to "Предпросмотр в реальном времени",
        "Cloud" to "Облачные сервисы",
        "Personal vocabulary" to "Личный словарь",
        "Invocation" to "Запуск диктовки",
        "Voice keyboard" to "Голосовая клавиатура",
        "History" to "История",
        "Advanced" to "Дополнительно",
        "Behavior" to "Поведение",
        "Updates" to "Обновления",
        "Check for and manage app updates" to "Проверить обновления приложения",
        "Data & Logs" to "Данные и журналы",
        "Overlay Appearance" to "Вид плавающей кнопки",
        "Audio permission" to "Доступ к микрофону",
        "Accessibility service" to "Служба специальных возможностей",
        "Use cloud transcription" to "Использовать облачное распознавание",
        "Uses the Cloud provider chain" to "Использует настроенные облачные сервисы",
        "Local models" to "Локальные модели",
        "Local cleanup models" to "Локальные модели корректировки",
        "Fallback: cloud providers" to "Резерв: облачные сервисы",
        "Cloud provider chain" to "Цепочка облачных сервисов",
        "Language" to "Язык",
        "Dictation language" to "Язык диктовки",
        "Effective routing" to "Текущий маршрут обработки",
        "Improve my dictation with AI" to "Улучшать текст с помощью ИИ",
        "Style" to "Стиль",
        "How cleanup runs" to "Способ корректировки",
        "Local" to "Локально",
        "Private, runs on-device, no API key needed" to "Работает на устройстве, ключ API не нужен",
        "Configure providers and keys under Cloud" to "Настройте сервисы и ключи в разделе «Облако»",
        "Mode" to "Режим",
        "Floating icon (default)" to "Плавающая кнопка (по умолчанию)",
        "System controls" to "Системные элементы управления",
        "Ramblr keyboard" to "Клавиатура Ramblr",
        "How to start dictation" to "Как начать диктовку",
        "Floating ring" to "Плавающий значок",
        "System accessibility button / gesture" to "Системная кнопка или жест",
        "Volume-keys hold" to "Удержание кнопок громкости",
        "Turn Ramblr off" to "Выключить Ramblr",
        "Direct shortcut control" to "Прямое управление ярлыком",
        "Icon is currently hidden" to "Значок сейчас скрыт",
        "Tap to show it again" to "Нажмите, чтобы показать его",
        "Snippets" to "Шаблоны текста",
        "Vocabulary" to "Словарь",
        "Advanced tuning" to "Дополнительные параметры",
        "Local transcription threads" to "Потоки локального распознавания",
        "Canary language" to "Язык модели Canary",
        "Suggested terms" to "Предлагаемые слова",
        "Icon size" to "Размер значка",
        "Border color" to "Цвет рамки",
        "Fill color (idle only)" to "Цвет заливки (в покое)",
        "Microphone icon color" to "Цвет микрофона",
        "Custom icon image" to "Свой значок",
        "Remove custom icon" to "Удалить свой значок",
        "Go back to the default mic icon" to "Вернуть обычный значок микрофона",
        "Dictation mode" to "Режим диктовки",
        "Fallback behavior" to "Резервные варианты",
        "Add provider" to "Добавить сервис",
        "Advanced: per-feature override" to "Дополнительно: настройка по функциям",
        "Experimental" to "Экспериментальные функции",
        "Enabled" to "Включено",
        "Model" to "Модель",
        "Transcription model" to "Модель распознавания",
        "Advanced: enter a custom model id" to "Дополнительно: указать ID модели",
        "Advanced: enter a custom transcription model id" to "Дополнительно: указать ID модели распознавания",
        "Advanced: base URL override" to "Дополнительно: другой адрес сервиса",
        "Use for" to "Использовать для",
        "Credential" to "Ключ доступа",
        "Dictation history" to "История диктовок",
        "Recover past transcripts, tap to copy" to "Нажмите, чтобы скопировать прежний текст",
        "Backup & restore" to "Резервное копирование и восстановление",
        "Diagnostic logs" to "Журналы диагностики",
        "No dictations yet" to "Диктовок пока нет",
        "App exclusions" to "Исключения для приложений",
        "Add" to "Добавить",
        "No apps excluded yet" to "Исключений пока нет",
        "+ New snippet" to "+ Новый шаблон",
        "Add a trigger phrase and its expansion" to "Добавьте фразу и текст для подстановки",
        "No snippets configured yet." to "Шаблонов пока нет.",
        "Manage styles" to "Управление стилями",
        "+ New style" to "+ Новый стиль",
        "Write your own cleanup prompt" to "Напишите свою инструкцию для корректировки",
        "Dismiss" to "Закрыть",
        "Installing..." to "Установка…",
        "Extracting..." to "Распаковка…",
        "Off" to "Выключено",
        "Cloud · set up a provider" to "Облако · настройте сервис",
        "Local · no model installed yet" to "Локально · модель ещё не установлена",
        "Permission and Ramblr Voice enabled" to "Доступ к микрофону и голосовая клавиатура включены",
        "Audio permission and Ramblr Voice needed" to "Нужны доступ к микрофону и голосовая клавиатура",
        "Audio permission needed" to "Нужен доступ к микрофону",
        "Ramblr Voice needs enabling" to "Нужно включить голосовую клавиатуру",
        "Required — choose how speech becomes text. Local (default): fastest, fully private, works with no internet. Cloud: uses your Cloud provider chain." to
            "Выберите способ распознавания речи. Локальный режим работает без интернета и сохраняет запись на устройстве. Облачный режим использует настроенные сервисы.",
        "Manage styles, quick-menu selection, and custom prompts" to "Управление стилями, быстрым меню и своими инструкциями",
        "One ordered list of cloud providers, shared by transcription and cleanup. Each feature automatically uses the first entry in the list that supports it. On-device always works underneath, with no setup needed." to
            "Общий упорядоченный список облачных сервисов для распознавания и корректировки. Для каждой функции выбирается первый подходящий сервис. Локальная обработка доступна без настройки.",
        "Pick a preset, or fine-tune with the toggles below. Each mode controls both how your speech is transcribed and how the text gets cleaned up." to
            "Выберите готовый режим или настройте переключатели ниже. Режим управляет распознаванием речи и корректировкой текста.",
        "These control what happens automatically when your current mode's provider fails." to
            "Здесь задаётся поведение приложения при сбое выбранного сервиса.",
        "Fall back to on-device if cloud fails" to "Использовать устройство при сбое облака",
        "Fall back to cloud if on-device fails" to "Использовать облако при локальном сбое",
        "No cloud providers configured yet. On-device transcription and cleanup are used instead." to
            "Облачные сервисы ещё не настроены. Используется локальное распознавание и корректировка.",
        "Usually set together by the mode preset above. Only change these individually if you want a mix, e.g. cloud transcription with on-device cleanup." to
            "Обычно эти параметры задаются выбранным режимом. Изменяйте их отдельно, если нужен смешанный вариант, например облачное распознавание и локальная корректировка.",
        "Always on-device — a cloud round trip would defeat the point of showing text live while you speak" to
            "Всегда работает на устройстве: задержка облака мешала бы показывать текст во время речи",
        "Live cloud transcription" to "Потоковое облачное распознавание",
        "Save dictation history" to "Сохранять историю диктовок",
        "Keeps past transcripts on-device so a failed insertion isn't lost" to "Сохраняет прежние тексты на устройстве, чтобы они не пропали при ошибке вставки",
        "Saves dictation history, benchmark log, and settings to a file you choose where to send. API keys are never included -- they're encrypted to this device's hardware Keystore and can't be restored elsewhere, so you'll re-enter them once on a new device. The quality log is never included either: it contains your actual dictated text, so it stays strictly on this device." to
            "Сохраняет историю диктовок, журнал измерений и настройки в файл, который можно отправить в выбранное приложение. Ключи API в копию не входят: они защищены хранилищем этого устройства, поэтому на новом устройстве их нужно ввести заново. Журнал качества также не входит в копию, поскольку содержит тексты ваших диктовок.",
        "Backup all data" to "Создать резервную копию",
        "Share a backup file containing history, the benchmark log, and settings" to "Отправить файл с историей, журналом измерений и настройками",
        "Restore from backup" to "Восстановить из копии",
        "Pick a backup file to restore. This overwrites existing history, benchmark log, and settings" to "Выберите файл копии. Текущая история, журнал измерений и настройки будут заменены",
        "Share benchmark log" to "Отправить журнал измерений",
        "Send the on-device transcription/cleanup benchmark log (timings and model ids only, no dictation text) to any app" to "Отправить время работы и названия моделей без текстов диктовок",
        "Save quality log" to "Сохранять журнал качества",
        "Keeps the raw and cleaned text of every dictation on-device for provider/model quality review. Off by default" to "Сохраняет исходный и исправленный тексты диктовок на устройстве для оценки качества моделей. По умолчанию выключено",
        "Share quality log" to "Отправить журнал качества",
        "Send the on-device raw/cleaned transcript pairs (real text) and provider/model ids to any app" to "Отправить исходные и исправленные тексты с названиями сервисов и моделей",
        "Restore from backup?" to "Восстановить из копии?",
        "Turn off quality logging" to "Выключить журнал качества",
        "New dictations won't be added to the quality log. Delete the transcript text already saved in it?" to "Новые диктовки не будут записываться в журнал качества. Удалить уже сохранённые тексты?",
        "Delete log" to "Удалить журнал",
        "Keep log" to "Оставить журнал",
        "Turn off dictation history" to "Выключить историю диктовок",
        "New dictations won't be saved. Clear the transcripts already saved on this device?" to "Новые диктовки не будут сохраняться. Очистить тексты, уже сохранённые на устройстве?",
        "Clear history" to "Очистить историю",
        "Keep history" to "Оставить историю",
        "Clear all history?" to "Очистить всю историю?",
        "Removes every saved transcript from this device. This can't be undone." to "Удаляет все сохранённые диктовки с устройства. Это действие нельзя отменить.",
        "Delete this transcript?" to "Удалить эту диктовку?",
        "Customize how the floating mic button looks. A custom icon image replaces the built-in circle entirely, so border/fill colors stop applying once one is set." to
            "Настройте вид плавающей кнопки микрофона. Свой значок полностью заменяет обычный круг, поэтому цвета рамки и заливки при его использовании не применяются.",
        "Small" to "Маленький",
        "Medium (default)" to "Средний (по умолчанию)",
        "Large" to "Большой",
        "Default" to "По умолчанию",
        "White" to "Белый",
        "Black" to "Чёрный",
        "Emerald" to "Изумрудный",
        "Sky blue" to "Голубой",
        "Violet" to "Фиолетовый",
        "Amber" to "Янтарный",
        "Slate" to "Серый",
        "Custom image set" to "Свой значок установлен",
        "Not set — using the default mic icon" to "Не задан — используется обычный значок микрофона",
        "Not used while a custom icon image is set" to "Не применяется при использовании своего значка",
        "Saturation" to "Насыщенность",
        "Brightness" to "Яркость",
        "Opacity" to "Непрозрачность",
        "Hue" to "Оттенок",
        "Use this color" to "Выбрать цвет",
        "Reset to default" to "Вернуть цвет по умолчанию",
        "Project names or jargon that speech-to-text often mishears. One per line.\n\nApplies to cloud transcription and to cleanup (cloud and local)." to
            "Названия проектов и специальные слова, которые распознаются неверно. По одному в строке.\n\nИспользуются при облачном распознавании и корректировке текста — облачной или локальной.",
        "Your current setup is local transcription with cleanup off, so these terms are not used: local transcription doesn't support vocabulary biasing. They apply once cleanup is enabled." to
            "Сейчас используется локальное распознавание без корректировки, поэтому словарь не применяется. Он начнёт работать после включения корректировки.",
        "One term per line, e.g. FastHTML" to "По одному слову в строке, например FastHTML",
        "Active — Ramblr's own floating ring starts dictation; the service has a simple independent on/off switch in system Settings" to
            "Активно — плавающая кнопка Ramblr запускает диктовку; службу можно отдельно включать и выключать в настройках системы",
        "Ramblr's floating ring only — no system button, and the system Settings switch can't be tripped by shortcut changes" to
            "Только плавающая кнопка Ramblr; изменения системных ярлыков не затронут переключатель службы",
        "Active — the system accessibility button, gesture, or volume-keys hold starts dictation" to
            "Активно — диктовка запускается системной кнопкой, жестом или удержанием клавиш громкости",
        "Nav-bar/floating button, accessibility gesture, and volume-keys hold — switches instantly" to
            "Кнопка на панели или поверх экрана, жест и клавиши громкости — переключение без дополнительных действий",
        "Nav-bar/floating button, accessibility gesture, and volume-keys hold — needs one enable tap in system Settings" to
            "Кнопка на панели или поверх экрана, жест и клавиши громкости — потребуется включить службу в настройках системы",
        "A dictation keyboard you can switch to in any text field. Independent of the mode above." to
            "Голосовая клавиатура доступна в любом текстовом поле и работает независимо от выбранного выше режима.",
        "A dictation keyboard you can switch to in any text field. Works alongside the mode above." to
            "Голосовая клавиатура доступна в любом текстовом поле и работает вместе с выбранным выше режимом.",
        "Off — tap to turn the Ramblr keyboard on in system settings" to "Выключена — нажмите, чтобы включить клавиатуру Ramblr в настройках системы",
        "On, but not your default keyboard — switch to it from the keyboard switcher, or tap to make it the default" to
            "Включена, но не выбрана по умолчанию — переключитесь на неё или нажмите, чтобы выбрать основной",
        "On — your default keyboard, opens automatically in text fields" to "Включена по умолчанию — открывается в текстовых полях автоматически",
        "On — tap the ring to start and stop dictation" to "Включена — нажмите значок, чтобы начать или остановить диктовку",
        "Off — the ring is hidden" to "Выключена — значок скрыт",
        "Requires System controls mode" to "Требуется режим системных элементов управления",
        "Requires System controls mode — in Floating icon mode this shortcut would turn Ramblr itself off and on" to
            "Требуется режим системных элементов управления. В режиме плавающей кнопки это сочетание включало бы и выключало сам Ramblr",
        "On — the system button/gesture starts dictation" to "Включено — системная кнопка или жест запускает диктовку",
        "On — hold both volume keys to start dictation" to "Включено — удерживайте обе клавиши громкости для запуска диктовки",
        "tap to switch in-app" to "нажмите для переключения",
        "tap to open system settings" to "нажмите для открытия системных настроек",
        "Dictate from the Quick Settings panel — tap to add the tile" to "Запускайте диктовку из быстрых настроек — нажмите, чтобы добавить плитку",
        "Dictate from the Quick Settings panel — tap for setup steps" to "Запускайте диктовку из быстрых настроек — нажмите для инструкции",
        "Quick Settings tile" to "Плитка быстрых настроек",
        "Debug / visibility" to "Диагностика и отображение",
        "Shows extra under-the-hood detail, like which dictations used a paid cleanup fallback" to "Показывает дополнительные сведения, например когда использовался платный резервный сервис корректировки",
        "Remember cleanup style per app" to "Запоминать стиль для каждого приложения",
        "Auto-selects the last style you picked for each app instead of always using your global default" to "Автоматически выбирает последний стиль, использованный в этом приложении",
        "Allow hiding the floating icon" to "Разрешить скрывать плавающую кнопку",
        "Adds a 'Hide icon' option to the long-press menu, so you can fully hide the icon and bring it back from a notification" to "Добавляет пункт «Скрыть значок» в меню долгого нажатия. Вернуть его можно через уведомление",
        "Let automation apps turn Ramblr off" to "Разрешить автоматизации выключать Ramblr",
        "Adds a broadcast MacroDroid or Tasker can send to switch the accessibility service off — more reliable than having them edit the accessibility setting directly. Any app on your phone can send it, so leave this off unless you use it" to
            "Позволяет MacroDroid или Tasker отключать службу специальных возможностей командой. Это надёжнее прямого изменения системной настройки. Команду может отправить любое приложение, поэтому включайте её только при необходимости",
        "Auto-hide icon when idle" to "Автоматически скрывать кнопку при простое",
        "Slides the icon toward the screen edge after a few seconds of inactivity. Turn off to keep it fully visible at all times" to "Через несколько секунд бездействия кнопка смещается к краю экрана. Выключите, чтобы она оставалась полностью видимой",
        "Auto-hide delay" to "Задержка перед скрытием",
        "Peeked sliver size" to "Видимая часть кнопки у края",
        "Single-tap restore and record" to "Восстановление и запись одним нажатием",
        "While peeked, one tap both brings the icon back and starts recording. Off by default, which keeps the first tap just restoring the icon and a second tap starting recording" to "Когда кнопка скрыта у края, одно нажатие возвращает её и начинает запись. По умолчанию первое нажатие только возвращает кнопку, а второе начинает запись",
        "Offer raw text after cleanup" to "Предлагать исходный текст после корректировки",
        "Shows a \"Tap to use raw text\" bubble for a few seconds after cleanup changes your wording, so you can undo it with one tap" to "После изменения текста на несколько секунд показывает кнопку возврата к исходной версии",
        "Auto-stop after silence" to "Останавливать запись после паузы",
        "Silence threshold" to "Длительность паузы",
        "Compress audio before cloud upload" to "Сжимать аудио перед отправкой в облако",
        "Encodes to AAC/M4A before sending to OpenAI or Gemini for transcription -- primarily useful on cellular or slow connections. Transcription quality with compressed audio hasn't been verified in real-world use yet, so leave off for dictations where accuracy really matters until you've tried it" to
            "Кодирует запись в AAC/M4A перед отправкой в OpenAI или Gemini. Полезно при медленном соединении, но влияние сжатия на точность пока не проверено в реальных условиях",
        "Smart vocabulary suggestions" to "Предлагать слова для словаря",
        "Notices words dictation keeps correcting or that keep recurring, and suggests adding them to your vocabulary. Everything stays on this device; turning this off also deletes what's been noticed so far" to
            "Находит часто повторяющиеся или исправляемые слова и предлагает добавить их в словарь. Данные остаются на устройстве; при выключении накопленные предложения удаляются",
        "Dismissed suggestions" to "Отклонённые предложения",
        "Custom silence threshold" to "Своя длительность паузы",
        "Off. Stops recording automatically after a pause in speech" to "Выключено. При включении запись автоматически останавливается после паузы в речи",
        "Downloading silence-detection model…" to "Загрузка модели определения тишины…",
        "Will activate once the silence-detection model finishes downloading" to "Заработает после загрузки модели определения тишины",
        "Only affects Canary; other models ignore this" to "Влияет только на Canary; другие модели игнорируют эту настройку",
        "Auto-hide, per-app style, vocabulary, and more" to "Автоматическое скрытие, стили для приложений, словарь и другие параметры",
        "Shows live partial text in the focused field as you speak, using a separate on-device streaming model. The final text (after you tap to stop) still comes from your regular transcription + cleanup settings — this only changes what's shown while recording." to
            "Показывает предварительный текст во время речи с помощью отдельной локальной модели. Итоговый текст после остановки записи создаётся обычными настройками распознавания и корректировки.",
        "Streaming live preview" to "Предпросмотр во время речи",
        "Streaming preview model" to "Модель предпросмотра",
        "Custom — transcription and cleanup are set independently below." to "Свой режим — распознавание и корректировка настраиваются отдельно ниже.",
        "Unsupported: typos or retired model ids will fail at call time with no extra validation." to "Модель не проверяется заранее: ошибка в названии или устаревший ID приведут к сбою при вызове.",
        "This removes it from the provider chain and clears its saved key." to "Сервис будет удалён из цепочки вместе с сохранённым ключом.",
        "History on · backup, benchmark & quality logs" to "История включена · резервные копии и журналы",
        "History off · backup, benchmark & quality logs" to "История выключена · резервные копии и журналы",
        "Setup required — tap to finish setup" to "Требуется настройка — нажмите, чтобы закончить",
        "Ready — select Ramblr Voice to dictate" to "Готово — выберите Ramblr Voice для диктовки",
        "Ready — tap the overlay dot to dictate" to "Готово — нажмите плавающую кнопку для диктовки",
        "Ramblr was turned off by the system shortcut switch" to "Ramblr выключен системным переключателем ярлыка",
        "Ramblr's accessibility service was turned off" to "Служба специальных возможностей Ramblr выключена",
        "Tap to turn it back on." to "Нажмите, чтобы снова включить.",
        "Tap to open its Accessibility page and turn it back on." to "Нажмите, чтобы открыть системные настройки и включить службу.",
        "Floating icon mode" to "Режим плавающей кнопки",
        "System controls mode" to "Режим системных элементов управления",
        "ring on" to "кнопка включена",
        "system button on" to "системная кнопка включена",
        "volume keys on" to "клавиши громкости включены",
        "Ramblr's accessibility service is off — turn it back on from the setup screen" to "Служба Ramblr выключена — включите её на экране настройки",
        "Stops dictation everywhere and releases the accessibility service. In this mode Android hides the off switch on Ramblr's system Accessibility page, so this is it." to
            "Останавливает диктовку и выключает службу специальных возможностей. В этом режиме Android скрывает системный переключатель службы.",
        "Stops dictation everywhere and releases the accessibility service. Same as the switch on Ramblr's system Accessibility page." to
            "Останавливает диктовку и выключает службу специальных возможностей, как системный переключатель Ramblr.",
        "Switch to System controls mode first" to "Сначала включите режим системных элементов управления",
        "Before you go: how this switch behaves" to "Перед переключением режима",
        "Add the Ramblr tile" to "Добавить плитку Ramblr",
        "Turn Ramblr off?" to "Выключить Ramblr?",
        "Turn off" to "Выключить",
        "Nothing will turn it back on by itself." to "Служба не включится сама.",
        "Microphone access needed" to "Нужен доступ к микрофону",
        "One extra step needed" to "Нужен ещё один шаг",
        "Cleanup sends text off-device" to "Корректировка отправит текст с устройства",
        "No snippets configured" to "Шаблонов пока нет",
        "No snippets configured yet." to "Шаблонов пока нет.",
        "This can't be undone." to "Это действие нельзя отменить.",
        "New snippet" to "Новый шаблон",
        "Edit snippet" to "Изменить шаблон",
        "New style" to "Новый стиль",
        "Edit style" to "Изменить стиль",
        "Skip" to "Пропустить",
        "Open App info" to "Открыть сведения о приложении",
        "Open Accessibility Settings" to "Открыть настройки специальных возможностей",
        "Open Keyboard Settings" to "Открыть настройки клавиатуры",
        "Floating button" to "Плавающая кнопка",
        "Use on-device (recommended)" to "Локально (рекомендуется)",
        "Use cloud (needs API key)" to "Облако (нужен ключ API)",
        "Use on-device" to "Локально",
        "Use cloud" to "Облако",
        "Skip (leave off)" to "Пропустить (оставить выключенным)",
        "Skip (recommended)" to "Пропустить (рекомендуется)",
        "Turn on" to "Включить",
        "Finish setup" to "Завершить настройку",
        "Try it out (optional)" to "Попробуйте диктовку (необязательно)",
        "Step 1 of 5: Microphone access" to "Шаг 1 из 5: доступ к микрофону",
        "Step 2 of 5: Turn on Accessibility" to "Шаг 2 из 5: включение специальных возможностей",
        "Step 2 of 5: Enable Ramblr Voice" to "Шаг 2 из 5: включение Ramblr Voice",
        "Step 3 of 5: Choose transcription mode" to "Шаг 3 из 5: способ распознавания",
        "Step 4 of 5: Clean up dictation with AI? (optional)" to "Шаг 4 из 5: корректировка текста ИИ (необязательно)",
        "Step 4b of 5: Choose a cloud cleanup provider" to "Шаг 4 из 5: облачный сервис корректировки",
        "Step 5 of 5: Show live text while you speak? (optional)" to "Шаг 5 из 5: показывать текст во время речи (необязательно)",
        "Welcome to Ramblr" to "Добро пожаловать в Ramblr",
        "This overwrites the dictation history, benchmark log, and settings currently on this device with the contents of the selected file. This can't be undone." to
            "История диктовок, журнал измерений и настройки на устройстве будут заменены данными из выбранного файла. Это действие нельзя отменить.",
        "Rose" to "Розовый",
        "Enable snippets" to "Включить шаблоны текста",
        "Off -- configured snippets are kept but never expanded" to "Выключено — сохранённые шаблоны не подставляются",
        "Trigger phrase, e.g. my home address" to "Фраза для замены, например мой адрес",
        "Text to insert instead" to "Текст для подстановки",
        "Style name" to "Название стиля",
        "Prompt sent to the cleanup model" to "Инструкция для модели корректировки",
        "This can't be undone. Any app that was set to use this style falls back to your global style." to
            "Это действие нельзя отменить. Приложения, использовавшие этот стиль, вернутся к общему стилю.",
        "Exact package name, e.g. com.example.bank" to "Точное имя пакета, например com.example.bank",
        "Ramblr stays out of the way in this app" to "Ramblr не будет работать в этом приложении",
        "Custom style" to "Свой стиль",
        "Active — shortcut changes apply in-app, without the system Settings round-trip" to "Активно — ярлыками можно управлять прямо в приложении",
        "Lets Ramblr manage system shortcuts directly — requires a one-time adb grant, tap for the command" to
            "Позволяет управлять системными ярлыками из приложения. Требуется однократная настройка через adb; нажмите, чтобы увидеть команду",
        "Redo setup, overlay appearance, behavior, updates, data & logs" to "Повторная настройка, вид кнопки, поведение, обновления и журналы",
        "Redo setup, overlay appearance, behavior, data & logs" to "Повторная настройка, вид кнопки, поведение и журналы",
        "History is off — tap to turn it on in Data & Logs" to "История выключена — нажмите, чтобы включить её в разделе «Данные и журналы»",
        "No dictations saved yet — recent ones appear here" to "Диктовок пока нет — здесь появятся последние записи",
        "1 saved dictation — tap one to copy it" to "1 диктовка — нажмите, чтобы скопировать текст",
        "No terms yet — names and jargon the models should get right" to "Слов пока нет — добавьте названия и термины для точного распознавания",
        "1 term — names and jargon the models should get right" to "1 слово — названия и термины для точного распознавания",
        "No custom terms" to "Нет своих слов",
        "Off — cloud transcription returns your text once you stop speaking" to "Выключено — облачное распознавание вернёт текст после окончания речи",
        "On, but transcription is set to on-device — needs cloud transcription above" to "Включено, но выбрано локальное распознавание — переключитесь на облачное выше",
        "On, but no Gemini key is set — add a Gemini provider above" to "Включено, но не указан ключ Gemini — добавьте сервис выше",
        "On — the keyboard streams to Gemini as you speak. Experimental, costs more than batch" to "Включено — клавиатура передаёт речь в Gemini в реальном времени. Экспериментальная функция с более высокой стоимостью",
        "Streams audio to Gemini while you speak, so text appears as you talk instead of after you stop. Ramblr keyboard only. Requires cloud transcription and a Gemini key — it does not change which provider transcribes you, or the order of the chain above. Unproven on real devices and roughly 1.8x the cost of the normal upload, so it is off until you turn it on. If a stream fails, the recording still finishes over the normal path." to
            "Передаёт звук в Gemini во время речи, чтобы текст появлялся сразу. Работает только с клавиатурой Ramblr; нужны облачное распознавание и ключ Gemini. Функция экспериментальная, дороже обычной отправки записи и по умолчанию выключена. При сбое запись обрабатывается обычным способом.",
    )

    fun translate(source: String): String = labels[source] ?: when {
        source.startsWith("Error: ") -> "Ошибка: " + source.removePrefix("Error: ")
        source.startsWith("Local · ") -> "Локально · " + source.removePrefix("Local · ")
        source.startsWith("Cloud · ") -> "Облако · " + source.removePrefix("Cloud · ")
        source.endsWith(" saved dictations — tap one to copy it") ->
            source.substringBefore(" saved dictations") + " диктовок — нажмите, чтобы скопировать текст"
        source.endsWith(" terms — names and jargon the models should get right") ->
            source.substringBefore(" terms") + " слов — названия и термины для точного распознавания"
        source.startsWith("Not used while cleanup is off — ") ->
            "Не используется, пока корректировка выключена — " + source.removePrefix("Not used while cleanup is off — ")
        source.startsWith("Floating icon mode — ") || source.startsWith("System controls mode — ") ->
            source.split(" — ", limit = 2).let { parts ->
                val mode = translate(parts[0])
                if (parts[1] == "no method currently active") "$mode — нет активного способа запуска"
                else "$mode — " + parts[1].split(", ").joinToString(", ") { translate(it) }
            }
        source.endsWith(" app excluded") -> source.substringBefore(" app excluded") + " приложение исключено"
        source.endsWith(" apps excluded") -> source.substringBefore(" apps excluded") + " приложений исключено"
        source.startsWith("Project names or jargon that speech-to-text often mishears. One per line.\n\n") ->
            source.replace(
                "Project names or jargon that speech-to-text often mishears. One per line.\n\nApplies to cloud transcription and to cleanup (cloud and local).",
                labels.getValue("Project names or jargon that speech-to-text often mishears. One per line.\n\nApplies to cloud transcription and to cleanup (cloud and local).")
            ).replace(
                "Your current setup is local transcription with cleanup off, so these terms are not used: local transcription doesn't support vocabulary biasing. They apply once cleanup is enabled.",
                labels.getValue("Your current setup is local transcription with cleanup off, so these terms are not used: local transcription doesn't support vocabulary biasing. They apply once cleanup is enabled.")
            )
        source.endsWith(" · tap to switch in-app") -> translate(source.removeSuffix(" · tap to switch in-app")) + " · нажмите для переключения"
        source.endsWith(" · tap to open system settings") -> translate(source.removeSuffix(" · tap to open system settings")) + " · нажмите для открытия системных настроек"
        else -> source
    }

    fun localize(view: View) {
        if (view is TextView && view.tag != "user_content") {
            if (view !is EditText) {
                val source = view.text.toString()
                val translated = translate(source)
                if (translated != source) view.text = translated
            }
            view.hint?.toString()?.let { labels[it] }?.let { view.hint = it }
        }
        if (view is ViewGroup) for (index in 0 until view.childCount) localize(view.getChildAt(index))
    }
}
