
locals {
  manifests_dir_rel = var.manifests_dir
  manifest_files_rel = sort(fileset(local.manifests_dir_rel, "*.yaml"))
  manifest_files     = [for f in local.manifest_files_rel : "${path.module}/${local.manifests_dir_rel}/${f}"]
}

resource "kubectl_manifest" "manifests" {
  for_each = toset(local.manifest_files)

  yaml_body = file(each.key)
}
