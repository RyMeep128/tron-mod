Add-Type -AssemblyName System.Drawing
$root = Split-Path -Parent $PSScriptRoot
$textureDir = Join-Path $root 'src/main/resources/assets/tronmod/textures/block'
New-Item -ItemType Directory -Force -Path $textureDir | Out-Null
$source = [Drawing.Bitmap]::new((Join-Path $root 'art_sources/legacy_dark_panel_master.png'))
$base = [Drawing.Bitmap]::new(32, 32, [Drawing.Imaging.PixelFormat]::Format32bppArgb)
$g = [Drawing.Graphics]::FromImage($base)
$g.InterpolationMode = [Drawing.Drawing2D.InterpolationMode]::NearestNeighbor
$g.PixelOffsetMode = [Drawing.Drawing2D.PixelOffsetMode]::Half
$g.DrawImage($source, 0, 0, 32, 32)
$g.Dispose(); $source.Dispose()

function Save-Texture([string]$name, [string]$kind, [Drawing.Color]$accent) {
    $image = $base.Clone()
    $draw = [Drawing.Graphics]::FromImage($image)
    $draw.SmoothingMode = [Drawing.Drawing2D.SmoothingMode]::None
    $pen = [Drawing.Pen]::new($accent, 2)
    $thin = [Drawing.Pen]::new([Drawing.Color]::FromArgb(180,$accent), 1)
    $brush = [Drawing.SolidBrush]::new([Drawing.Color]::FromArgb(210,$accent))
    switch ($kind) {
        'polished' { $draw.FillRectangle([Drawing.SolidBrush]::new([Drawing.Color]::FromArgb(35,120,180,200)),0,0,32,32) }
        'reinforced' { $draw.DrawRectangle($thin,2,2,27,27); $draw.DrawLine($thin,2,2,29,29); $draw.DrawLine($thin,29,2,2,29) }
        'ribbed' { for($x=2;$x-lt 32;$x+=5){$draw.DrawLine($thin,$x,0,$x,31)} }
        'vent' { for($y=5;$y-lt 29;$y+=4){$draw.DrawLine($thin,5,$y,26,$y)} }
        'floor' { $draw.DrawRectangle($thin,1,1,29,29); $draw.DrawLine($thin,1,16,30,16);$draw.DrawLine($thin,16,1,16,30) }
        'platform' { $draw.DrawRectangle($pen,1,1,29,29);$draw.DrawRectangle($thin,5,5,21,21) }
        'circuit' { $draw.DrawLine($pen,2,16,13,16);$draw.DrawLine($pen,13,16,13,5);$draw.DrawLine($pen,13,5,25,5);$draw.FillEllipse($brush,24,3,5,5);$draw.DrawLine($thin,19,16,30,16);$draw.DrawLine($thin,19,16,19,27) }
        'line' { $draw.FillRectangle($brush,14,0,4,32);$draw.DrawLine($thin,11,0,11,31);$draw.DrawLine($thin,20,0,20,31) }
        'light' { $draw.DrawRectangle($thin,3,3,25,25);$draw.FillRectangle($brush,6,13,20,6);$draw.DrawLine([Drawing.Pen]::new([Drawing.Color]::White,1),7,14,24,14);$draw.DrawLine($thin,7,21,24,21) }
        'glass' { $draw.Clear([Drawing.Color]::FromArgb(95,3,14,22));$draw.DrawRectangle($pen,0,0,31,31);$draw.DrawLine($thin,2,29,29,2) }
        'facade' { for($x=3;$x-lt 30;$x+=7){$draw.DrawLine($pen,$x,1,$x,30)};$draw.DrawLine($thin,0,8,31,8);$draw.DrawLine($thin,0,24,31,24) }
        'road' { $draw.FillRectangle([Drawing.SolidBrush]::new([Drawing.Color]::FromArgb(90,0,0,0)),0,0,32,32);$draw.DrawLine($thin,0,2,31,2);$draw.DrawLine($thin,0,29,31,29) }
        'conduit' { $draw.DrawLine($pen,6,0,6,31);$draw.DrawLine($pen,16,0,16,31);$draw.DrawLine($thin,26,0,26,31);for($y=4;$y-lt 32;$y+=8){$draw.FillRectangle($brush,4,$y,5,3)} }
        'ore' { for($i=0;$i-lt 10;$i++){ $x=($i*13)%29;$y=($i*19)%29;$draw.FillRectangle($brush,$x,$y,3,3) } }
        'terminal' { $draw.FillRectangle($brush,5,4,22,13);$draw.DrawRectangle($pen,3,2,26,17);$draw.DrawLine($thin,5,24,27,24) }
        'device' { $draw.DrawEllipse($pen,4,4,24,24);$draw.DrawEllipse($thin,9,9,14,14);$draw.FillEllipse($brush,14,14,4,4) }
        'hazard' { $draw.DrawLine($pen,0,0,31,31);$draw.DrawLine($pen,31,0,0,31);$draw.DrawEllipse($thin,6,6,20,20) }
    }
    $draw.Dispose(); $pen.Dispose(); $thin.Dispose(); $brush.Dispose()
    $image.Save((Join-Path $textureDir "$name.png"),[Drawing.Imaging.ImageFormat]::Png)
    if($kind -in @('circuit','line','light','facade','conduit','terminal','device','hazard')){
        $mask=[Drawing.Bitmap]::new(32,32,[Drawing.Imaging.PixelFormat]::Format32bppArgb)
        for($mx=0;$mx-lt 32;$mx++){for($my=0;$my-lt 32;$my++){$pixel=$image.GetPixel($mx,$my);$brightness=$pixel.R+$pixel.G+$pixel.B;if($brightness-gt 310){$mask.SetPixel($mx,$my,$pixel)}else{$mask.SetPixel($mx,$my,[Drawing.Color]::Transparent)}}}
        $mask.Save((Join-Path $textureDir "${name}_emissive.png"),[Drawing.Imaging.ImageFormat]::Png);$mask.Dispose()
    }
    $image.Dispose()
}

$cyan=[Drawing.Color]::FromArgb(255,35,238,255);$white=[Drawing.Color]::FromArgb(255,235,253,255);$orange=[Drawing.Color]::FromArgb(255,255,132,28);$magenta=[Drawing.Color]::FromArgb(255,255,32,210);$dark=[Drawing.Color]::FromArgb(255,45,76,92)
$specs = @{
 'dark_panel'=@('base',$dark);'polished_panel'=@('polished',$cyan);'reinforced_panel'=@('reinforced',$dark);'ribbed_panel'=@('ribbed',$dark);'vent_panel'=@('vent',$dark);'floor_panel'=@('floor',$dark);'platform_panel'=@('platform',$cyan);'portal_alloy'=@('reinforced',$white);
 'cyan_circuit_panel'=@('circuit',$cyan);'white_circuit_panel'=@('circuit',$white);'orange_circuit_panel'=@('circuit',$orange);'cyan_line_tile'=@('line',$cyan);'white_line_tile'=@('line',$white);'orange_line_tile'=@('line',$orange);'cyan_light_panel'=@('light',$cyan);'white_light_panel'=@('light',$white);'orange_light_panel'=@('light',$orange);
 'cyan_grid_glass'=@('glass',$cyan);'white_grid_glass'=@('glass',$white);'dark_grid_glass'=@('glass',$dark);'cyan_tower_facade'=@('facade',$cyan);'white_tower_facade'=@('facade',$white);'grid_road'=@('road',$cyan);'data_conduit'=@('conduit',$cyan);
 'grid_stone'=@('base',$dark);'circuit_tiles'=@('circuit',$cyan);'grid_shard_ore'=@('ore',$cyan);'grid_energy_field'=@('hazard',$magenta);'identity_terminal'=@('terminal',$cyan);'grid_access_device'=@('device',$white)
}
foreach($entry in $specs.GetEnumerator()){Save-Texture $entry.Key $entry.Value[0] $entry.Value[1]}
$base.Dispose()

$tilePreview=[Drawing.Bitmap]::new(256,256);$preview=[Drawing.Graphics]::FromImage($tilePreview);for($x=0;$x-lt 256;$x+=32){for($y=0;$y-lt 256;$y+=32){$img=[Drawing.Image]::FromFile((Join-Path $textureDir 'dark_panel.png'));$preview.DrawImage($img,$x,$y,32,32);$img.Dispose()}};$preview.Dispose();$tilePreview.Save((Join-Path $root 'art_sources/legacy_dark_panel_tiling_preview.png'),[Drawing.Imaging.ImageFormat]::Png);$tilePreview.Dispose()
$palettePreview=[Drawing.Bitmap]::new(768,512);$paletteDraw=[Drawing.Graphics]::FromImage($palettePreview);$paletteDraw.InterpolationMode=[Drawing.Drawing2D.InterpolationMode]::NearestNeighbor;$previewNames=@($specs.Keys|Sort-Object);for($i=0;$i-lt $previewNames.Count;$i++){$img=[Drawing.Image]::FromFile((Join-Path $textureDir "$($previewNames[$i]).png"));$paletteDraw.DrawImage($img,(($i%6)*128),([math]::Floor($i/6)*128),128,128);$img.Dispose()};$paletteDraw.Dispose();$palettePreview.Save((Join-Path $root 'art_sources/legacy_palette_preview.png'),[Drawing.Imaging.ImageFormat]::Png);$palettePreview.Dispose()

function Write-Utf8([string]$path,[string]$content){$parent=Split-Path -Parent $path;New-Item -ItemType Directory -Force -Path $parent|Out-Null;[IO.File]::WriteAllText($path,$content,[Text.UTF8Encoding]::new($false))}
$assetRoot=Join-Path $root 'src/main/resources/assets/tronmod';$dataRoot=Join-Path $root 'src/main/resources/data/tronmod'
$baseNames=@('dark_panel','polished_panel','reinforced_panel','ribbed_panel','vent_panel','floor_panel','platform_panel','portal_alloy','cyan_circuit_panel','white_circuit_panel','orange_circuit_panel','cyan_grid_glass','white_grid_glass','dark_grid_glass','grid_road')
$axisNames=@('cyan_line_tile','white_line_tile','orange_line_tile','cyan_light_panel','white_light_panel','orange_light_panel','cyan_tower_facade','white_tower_facade','data_conduit')
$emissiveNames=@('cyan_circuit_panel','white_circuit_panel','orange_circuit_panel','cyan_light_panel','white_light_panel','orange_light_panel','cyan_line_tile','white_line_tile','orange_line_tile','cyan_tower_facade','white_tower_facade','data_conduit')
$allNew=$baseNames+$axisNames
foreach($name in $baseNames){
 Write-Utf8 (Join-Path $assetRoot "blockstates/$name.json") "{`"variants`":{`"`":{`"model`":`"tronmod:block/$name`"}}}"
 Write-Utf8 (Join-Path $assetRoot "models/block/$name.json") "{`"parent`":`"minecraft:block/cube_all`",`"textures`":{`"all`":`"tronmod:block/$name`"}}"
}
foreach($name in $axisNames){
 Write-Utf8 (Join-Path $assetRoot "blockstates/$name.json") "{`"variants`":{`"axis=y`":{`"model`":`"tronmod:block/$name`"},`"axis=z`":{`"model`":`"tronmod:block/$name`",`"x`":90},`"axis=x`":{`"model`":`"tronmod:block/$name`",`"x`":90,`"y`":90}}}"
 Write-Utf8 (Join-Path $assetRoot "models/block/$name.json") "{`"parent`":`"minecraft:block/cube_column`",`"textures`":{`"end`":`"tronmod:block/dark_panel`",`"side`":`"tronmod:block/$name`"}}"
}
$faces='"down":{"texture":"#tex","cullface":"down"},"up":{"texture":"#tex","cullface":"up"},"north":{"texture":"#tex","cullface":"north"},"south":{"texture":"#tex","cullface":"south"},"west":{"texture":"#tex","cullface":"west"},"east":{"texture":"#tex","cullface":"east"}'
foreach($name in $emissiveNames){$baseFaces=$faces.Replace('#tex','#base');$glowFaces=$faces.Replace('#tex','#glow');Write-Utf8 (Join-Path $assetRoot "models/block/$name.json") "{`"ambientocclusion`":false,`"textures`":{`"particle`":`"tronmod:block/$name`",`"base`":`"tronmod:block/$name`",`"glow`":`"tronmod:block/${name}_emissive`"},`"elements`":[{`"from`":[0,0,0],`"to`":[16,16,16],`"faces`":{$baseFaces}},{`"from`":[0,0,0],`"to`":[16,16,16],`"shade`":false,`"light_emission`":15,`"faces`":{$glowFaces}}]}"}
foreach($name in @('cyan_grid_glass','white_grid_glass','dark_grid_glass')){Write-Utf8 (Join-Path $assetRoot "models/block/$name.json") "{`"parent`":`"minecraft:block/cube_all`",`"render_type`":`"minecraft:translucent`",`"textures`":{`"all`":`"tronmod:block/$name`"}}"}
foreach($name in $allNew){
 Write-Utf8 (Join-Path $assetRoot "items/$name.json") "{`"model`":{`"type`":`"minecraft:model`",`"model`":`"tronmod:block/$name`"}}"
 Write-Utf8 (Join-Path $assetRoot "models/item/$name.json") "{`"parent`":`"tronmod:block/$name`"}"
 Write-Utf8 (Join-Path $dataRoot "loot_table/blocks/$name.json") "{`"type`":`"minecraft:block`",`"pools`":[{`"rolls`":1,`"entries`":[{`"type`":`"minecraft:item`",`"name`":`"tronmod:$name`"}],`"conditions`":[{`"condition`":`"minecraft:survives_explosion`"}]}]}"
 Write-Utf8 (Join-Path $dataRoot "recipe/$name.json") "{`"type`":`"minecraft:stonecutting`",`"ingredient`":`"tronmod:grid_stone`",`"result`":{`"id`":`"tronmod:$name`",`"count`":1}}"
}
$shapeBases=@('dark_panel','polished_panel','reinforced_panel','platform_panel')
foreach($baseName in $shapeBases){
 $slab="${baseName}_slab";$stairs="${baseName}_stairs";$wall="${baseName}_wall"
 Write-Utf8 (Join-Path $assetRoot "models/block/$slab.json") "{`"parent`":`"minecraft:block/slab`",`"textures`":{`"bottom`":`"tronmod:block/$baseName`",`"top`":`"tronmod:block/$baseName`",`"side`":`"tronmod:block/$baseName`"}}"
 Write-Utf8 (Join-Path $assetRoot "models/block/${slab}_top.json") "{`"parent`":`"minecraft:block/slab_top`",`"textures`":{`"bottom`":`"tronmod:block/$baseName`",`"top`":`"tronmod:block/$baseName`",`"side`":`"tronmod:block/$baseName`"}}"
 Write-Utf8 (Join-Path $assetRoot "blockstates/$slab.json") "{`"variants`":{`"type=bottom`":{`"model`":`"tronmod:block/$slab`"},`"type=top`":{`"model`":`"tronmod:block/${slab}_top`"},`"type=double`":{`"model`":`"tronmod:block/$baseName`"}}}"
 Write-Utf8 (Join-Path $assetRoot "models/block/$stairs.json") "{`"parent`":`"minecraft:block/stairs`",`"textures`":{`"bottom`":`"tronmod:block/$baseName`",`"top`":`"tronmod:block/$baseName`",`"side`":`"tronmod:block/$baseName`"}}"
 Write-Utf8 (Join-Path $assetRoot "models/block/${stairs}_inner.json") "{`"parent`":`"minecraft:block/inner_stairs`",`"textures`":{`"bottom`":`"tronmod:block/$baseName`",`"top`":`"tronmod:block/$baseName`",`"side`":`"tronmod:block/$baseName`"}}"
 Write-Utf8 (Join-Path $assetRoot "models/block/${stairs}_outer.json") "{`"parent`":`"minecraft:block/outer_stairs`",`"textures`":{`"bottom`":`"tronmod:block/$baseName`",`"top`":`"tronmod:block/$baseName`",`"side`":`"tronmod:block/$baseName`"}}"
 $stairVariants=@{};foreach($facing in @('east','west','south','north')){foreach($half in @('bottom','top')){foreach($shape in @('straight','inner_left','inner_right','outer_left','outer_right')){$key="facing=$facing,half=$half,shape=$shape";$model=$stairs;if($shape.StartsWith('inner')){$model="${stairs}_inner"}elseif($shape.StartsWith('outer')){$model="${stairs}_outer"};$rot=@{east=0;south=90;west=180;north=270}[$facing];if($shape.EndsWith('left')){$rot=($rot+270)%360}elseif($shape.EndsWith('right')){$rot=($rot+90)%360};$value=[ordered]@{model="tronmod:block/$model";y=$rot;uvlock=$true};if($half-eq'top'){$value.x=180};$stairVariants[$key]=$value}}}
 Write-Utf8 (Join-Path $assetRoot "blockstates/$stairs.json") (([ordered]@{variants=$stairVariants}|ConvertTo-Json -Depth 8 -Compress))
 Write-Utf8 (Join-Path $assetRoot "models/block/${wall}_post.json") "{`"parent`":`"minecraft:block/template_wall_post`",`"textures`":{`"wall`":`"tronmod:block/$baseName`"}}"
 Write-Utf8 (Join-Path $assetRoot "models/block/${wall}_side.json") "{`"parent`":`"minecraft:block/template_wall_side`",`"textures`":{`"wall`":`"tronmod:block/$baseName`"}}"
 Write-Utf8 (Join-Path $assetRoot "models/block/${wall}_side_tall.json") "{`"parent`":`"minecraft:block/template_wall_side_tall`",`"textures`":{`"wall`":`"tronmod:block/$baseName`"}}"
 Write-Utf8 (Join-Path $assetRoot "models/block/${wall}_inventory.json") "{`"parent`":`"minecraft:block/wall_inventory`",`"textures`":{`"wall`":`"tronmod:block/$baseName`"}}"
 $wallParts=@([ordered]@{when=[ordered]@{up='true'};apply=[ordered]@{model="tronmod:block/${wall}_post"}});foreach($direction in @('north','east','south','west')){$rotation=@{north=0;east=90;south=180;west=270}[$direction];foreach($height in @('low','tall')){$model=if($height-eq'tall'){"tronmod:block/${wall}_side_tall"}else{"tronmod:block/${wall}_side"};$apply=[ordered]@{model=$model;uvlock=$true};if($rotation-ne 0){$apply.y=$rotation};$wallParts+=[ordered]@{when=[ordered]@{$direction=$height};apply=$apply}}};Write-Utf8 (Join-Path $assetRoot "blockstates/$wall.json") (([ordered]@{multipart=$wallParts}|ConvertTo-Json -Depth 8 -Compress))
 foreach($shape in @($slab,$stairs,$wall)){$shapeModel=$shape;if($shape.EndsWith('_wall')){$shapeModel="${shape}_inventory"};Write-Utf8 (Join-Path $assetRoot "items/$shape.json") "{`"model`":{`"type`":`"minecraft:model`",`"model`":`"tronmod:block/$shapeModel`"}}";Write-Utf8 (Join-Path $assetRoot "models/item/$shape.json") "{`"parent`":`"tronmod:block/$shapeModel`"}";Write-Utf8 (Join-Path $dataRoot "loot_table/blocks/$shape.json") "{`"type`":`"minecraft:block`",`"pools`":[{`"rolls`":1,`"entries`":[{`"type`":`"minecraft:item`",`"name`":`"tronmod:$shape`"}]}]}"}
 Write-Utf8 (Join-Path $dataRoot "recipe/$slab.json") "{`"type`":`"minecraft:crafting_shaped`",`"category`":`"building`",`"pattern`":[`"BBB`"],`"key`":{`"B`":`"tronmod:$baseName`"},`"result`":{`"id`":`"tronmod:$slab`",`"count`":6}}"
 Write-Utf8 (Join-Path $dataRoot "recipe/$stairs.json") "{`"type`":`"minecraft:crafting_shaped`",`"category`":`"building`",`"pattern`":[`"B  `",`"BB `",`"BBB`"],`"key`":{`"B`":`"tronmod:$baseName`"},`"result`":{`"id`":`"tronmod:$stairs`",`"count`":4}}"
 Write-Utf8 (Join-Path $dataRoot "recipe/$wall.json") "{`"type`":`"minecraft:crafting_shaped`",`"category`":`"building`",`"pattern`":[`"BBB`",`"BBB`"],`"key`":{`"B`":`"tronmod:$baseName`"},`"result`":{`"id`":`"tronmod:$wall`",`"count`":6}}"
}
$old=@('grid_stone','circuit_tiles','grid_shard_ore','grid_energy_field','identity_terminal','grid_access_device');foreach($name in $old){Write-Utf8 (Join-Path $assetRoot "models/block/$name.json") "{`"parent`":`"minecraft:block/cube_all`",`"textures`":{`"all`":`"tronmod:block/$name`"}}"}
