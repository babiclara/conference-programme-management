# Figma build sheet

Everything needed for M1's wireframes and M2's high-fidelity prototype and component set.
Values match `src/main/resources/static/css/app.css`, so the built screens already follow the
prototype.

## 0. Setup

1. Apply at <https://figma.com/education> — free Professional for students, takes a few days.
2. Create **one** Figma **Design** file (not Figma Make) named `Conference Programme P01`.
3. Create three pages, in this order: `Wireframes`, `Components`, `Screens`.

## Design tokens

| Token | Value | Used for |
|---|---|---|
| Ink | `#1C1D1F` | body text, headings |
| Muted | `#6B7076` | labels, table headers, hints |
| Line | `#E3E5E8` | borders, dividers |
| Accent | `#1F5FA9` | primary buttons, links |
| Background | `#FBFBFC` | page background |
| Surface | `#FFFFFF` | cards, tables, forms |
| Danger | `#A72020` | delete, error text |
| Danger surface | `#FDF1F1` | error banner background |
| Success | `#1F5C39` | flash message text |
| Success surface | `#F1F9F3` | flash message background |

Typeface **Inter**. Sizes: heading 26/600, section heading 19/600, body 16/400,
label 14/600, small 14/400, table header 12/600 uppercase with 4% letter spacing.

Corner radius 6. Page width 1440, content column 840 centred. Spacing uses multiples of 4.

## 1. Page `Wireframes`

Six frames, 1440x1024, named `W1`–`W6`. **Greyscale only** — white fill, `#CCCCCC` borders,
grey boxes for text. No colour, no real styling. This page proves you planned before styling,
so keep it crude and do not go back and prettify it.

| Frame | Contents |
|---|---|
| `W1 Conference list` | nav bar, page title, button top right, table with 3 columns and 2 rows |
| `W2 Conference detail` | back link, title, button pair, details panel, "Sessions" heading, table with 4 rows |
| `W3 Empty state` | same as W2, table replaced by a single bordered box with a line of text and a button |
| `W4 Session form` | back link, title, 5 stacked label+field pairs, two buttons at the bottom |
| `W5 Form with errors` | same as W4, with a banner box at the top and a short line under two fields |
| `W6 AI suggestions` | nav, title, three stacked cards each with a text block and two buttons |

## 2. Page `Components`

Six components, built with **auto layout** and made into components (right-click → Create
component). Not rectangles copied around — the marker checks this.

| Component | Variants / spec |
|---|---|
| `Button` | Variants `Primary`, `Secondary`. Auto layout, padding 8 top/bottom and 15 left/right, radius 6. Primary: fill Accent, text white. Secondary: fill white, border Line, text Ink. |
| `Text input` | Variants `Default`, `Error`. Label 14/600 above, field height 38, padding 9/11, radius 6, border `#CCD0D5`. Error: border Danger, fill `#FDF6F5`, plus a 14px Danger message below. |
| `Select` | Same frame as Text input plus a chevron 11px from the right edge. |
| `Table row` | Auto layout, horizontal, height 44, padding 11/14, bottom border Line. Four slots: title, track, status chip, actions. |
| `Status chip` | Auto layout, padding 2/9, radius 999, fill `#EEF1F5`, text 12px `#41464C`. Variants: `DRAFT`, `CALL_OPEN`, `CALL_CLOSED`, `PROPOSED`, `ACCEPTED`, `SCHEDULED`. |
| `Flash message` | Variants `Success`, `Error`. Padding 12/16, radius 6, 3px left border. Success: fill Success surface, left border `#2E7D4F`, text Success. Error: fill Danger surface, left border `#C0392B`, text Danger. |

## 3. Page `Screens`

Six frames, 1440x1024, built **from the components above**. Use the real data below so the
screens look like a working product.

### `S1 Conference list`
Nav bar reading "Conference Programme". Heading `Conferences`, `New conference` primary button
top right. Table, header row `NAME / DATES / STATUS`:

| Name | Dates | Status |
|---|---|---|
| Algebra DevDays 2027 | 21 Sep 2027 – 22 Sep 2027 | `DRAFT` |
| JavaCro 2027 | 12 May 2027 – 13 May 2027 | `CALL_CLOSED` |

### `S2 Conference detail`
Back link `← All conferences`. Heading `JavaCro 2027`, with `Edit` and `Delete` secondary
buttons top right. Details panel, label column 200 wide:

- Dates — 12 May 2027 – 13 May 2027
- Status — `CALL_CLOSED`
- Call for papers closes — 28 Feb 2027, 23:59

Then heading `Sessions` with an `Add session` primary button, and a table
`TITLE / TRACK / STATUS / (actions)`:

| Title | Track | Status |
|---|---|---|
| Migrating a Monolith, Slowly | Architecture | `PROPOSED` |
| Postgres Constraints as a Safety Net | Data | `SCHEDULED` |
| Testing Spring Boot Without Mocks | Testing | `ACCEPTED` |
| Virtual Threads in Practice | Platform | `SCHEDULED` |

Actions column: `Edit` in Accent, `Delete` in Danger, both plain text.

### `S3 Empty state`
Same as S2 but for `Algebra DevDays 2027`, dates 21–22 Sep 2027, status `DRAFT`. The sessions
table is replaced by a box with a dashed Line border, radius 6, padding 18, Muted text reading
"This conference has no sessions yet."

### `S4 Session form`
Back link `← JavaCro 2027`. Heading `New session`. A white card, radius 6, padding 22,
containing five fields in order: Title, Abstract (textarea, 5 rows), Track, Public summary
(textarea, 3 rows), Status (select). `Optional.` in Muted under Track and Public summary.
`Save` primary and `Cancel` secondary at the bottom.

### `S5 Session form with errors`
Same as S4 with:
- `Flash message / Error` at the top of the card: "Please correct the highlighted fields."
- Title empty, Error variant, message "Title must not be blank."
- Abstract containing "too short", Error variant, message "Abstract must be at least 20 characters."

### `S6 AI suggestions`
Heading `Suggested placements`. Three cards, each white, radius 6, padding 18, 12 apart,
containing a session title in 16/600, a line of Muted text, and `Accept` primary plus `Reject`
secondary buttons.

| Session | Suggestion | Reason |
|---|---|---|
| Testing Spring Boot Without Mocks | Hall A, 10:00–10:45 | Same track as the preceding talk, and the room fits the expected audience. |
| Migrating a Monolith, Slowly | Hall B, 11:00–11:45 | Speaker is free, and no other Architecture talk is in this slot. |
| Virtual Threads in Practice | Hall A, 09:00–09:45 | **Conflict:** Hall A is already taken in this slot. |

Give the third card an amber warning strip: fill `#FEF8EC`, left border `#B07D18`,
text `#7A5410`.

## 4. When the pages are done

Update the frame to template mapping in the README, and tell Claude — the CSS can then be
adjusted to match anything you changed.
