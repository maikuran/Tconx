name: Generate TConstruct Material Fluids

on:
  workflow_dispatch:
  push:
    branches:
      - main

permissions:
  contents: write

jobs:
  generate-material-fluids:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout
        uses: actions/checkout@v4
        with:
          fetch-depth: 0

      - name: Pull latest main
        run: |
          git fetch origin main
          git checkout main
          git pull --rebase origin main

      - name: Generate material_fluid recipes
        shell: bash
        run: |
          set -euo pipefail

          DEF_ROOT="src/main/resources/data/sakalti/tinkering/materials/definition"
          OUT_ROOT="src/main/resources/data/sakalti/recipes/material_fluid"

          if [ ! -d "$DEF_ROOT" ]; then
            echo "ERROR: Material definition directory not found:"
            echo "$DEF_ROOT"
            exit 1
          fi

          mkdir -p "$OUT_ROOT"

          found=0

          while IFS= read -r -d '' file; do
            found=1

            name="$(basename "$file" .json)"
            output="sakalti:${name}"
            fluid="sakalti:molten_${name}"

            echo "Generating: $OUT_ROOT/${name}.json"
            echo "  material: $output"
            echo "  fluid:    $fluid"

            # definition に fluid が明示されている場合はこちらを優先
            definition_fluid="$(
              jq -r '.fluid // empty' "$file"
            )"

            if [ -n "$definition_fluid" ]; then
              fluid="$definition_fluid"
            fi

            # temperature が definition に存在する場合は使用。
            # なければ 1000。
            temperature="$(
              jq -r '.temperature // 1000' "$file"
            )"

            jq -n \
              --arg fluid "$fluid" \
              --arg output "$output" \
              --argjson temperature "$temperature" \
              '{
                type: "tconstruct:material_fluid",
                fluid: {
                  fluid: $fluid,
                  amount: 100
                },
                temperature: $temperature,
                output: $output
              }' \
              > "$OUT_ROOT/${name}.json"

          done < <(
            find "$DEF_ROOT" \
              -type f \
              -name '*.json' \
              -print0
          )

          if [ "$found" -eq 0 ]; then
            echo "ERROR: No material definitions found."
            exit 1
          fi

          echo "Generated material_fluid recipes."

      - name: Validate generated JSON
        shell: bash
        run: |
          set -euo pipefail

          ROOT="src/main/resources/data/sakalti/recipes/material_fluid"

          count=0

          while IFS= read -r -d '' file; do
            count=$((count + 1))

            echo "Validating: $file"

            jq empty "$file"

            jq -e '
              .type == "tconstruct:material_fluid"
              and (.fluid | type == "object")
              and (.fluid.fluid | type == "string")
              and (.fluid.amount | type == "number")
              and (.fluid.amount > 0)
              and (.temperature | type == "number")
              and (.output | type == "string")
            ' "$file" > /dev/null

          done < <(
            find "$ROOT" \
              -type f \
              -name '*.json' \
              -print0
          )

          if [ "$count" -eq 0 ]; then
            echo "ERROR: No material_fluid recipes generated."
            exit 1
          fi

          echo "Validated $count material_fluid recipes."

      - name: Show generated recipes
        shell: bash
        run: |
          find \
            src/main/resources/data/sakalti/recipes/material_fluid \
            -type f \
            -name '*.json' \
            -print \
            -exec cat {} \;

      - name: Commit generated files
        shell: bash
        run: |
          set -euo pipefail

          git config user.name "github-actions[bot]"
          git config user.email "41898282+github-actions[bot]@users.noreply.github.com"

          git add \
            src/main/resources/data/sakalti/recipes/material_fluid/

          # 変更がなくても workflow 自体は成功させる
          if git diff --cached --quiet; then
            echo "No generated material_fluid changes."
            exit 0
          fi

          git commit -m "Generate TConstruct material fluid recipes"

      - name: Push generated files
        shell: bash
        run: |
          set -euo pipefail

          git fetch origin main
          git rebase origin/main

          git push origin HEAD:main
