name: Generate TConstruct Material Renders

on:
  push:
    branches:
      - main
  workflow_dispatch:

permissions:
  contents: write

jobs:
  generate-material-renders:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout repository
        uses: actions/checkout@v4
        with:
          fetch-depth: 0

      - name: Pull latest main
        run: |
          git pull --ff-only origin main

      - name: Set up Python
        uses: actions/setup-python@v5
        with:
          python-version: "3.x"

      - name: Generate material render JSON
        run: |
          python generate_material_renders.py

      - name: Validate JSON
        run: |
          python - <<'PY'
          import json
          from pathlib import Path

          root = Path(
              "src/main/resources/assets/sakalti/tinkering/materials"
          )

          files = sorted(root.glob("*.json"))

          if not files:
              raise SystemExit("No render JSON files found.")

          for path in files:
              with path.open("r", encoding="utf-8") as f:
                  json.load(f)

              print(f"[VALID] {path}")

          print(f"Validated {len(files)} render JSON files.")
          PY

      - name: Check for changes
        id: changes
        run: |
          if git diff --quiet; then
            echo "changed=false" >> "$GITHUB_OUTPUT"
          else
            echo "changed=true" >> "$GITHUB_OUTPUT"
          fi

      - name: Commit generated renders
        if: steps.changes.outputs.changed == 'true'
        run: |
          git config user.name "github-actions[bot]"
          git config user.email "41898282+github-actions[bot]@users.noreply.github.com"

          git add src/main/resources/assets/sakalti/tinkering/materials/

          git commit -m "Generate TConstruct material renders"

          git pull --rebase origin main

          git push origin main

      - name: No changes
        if: steps.changes.outputs.changed == 'false'
        run: |
          echo "Material renders are already up to date."
